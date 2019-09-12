/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixDataPurgingDao;
import bdsm.scheduler.model.FixDataPurging;
import bdsm.util.SchedulerUtil;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import static org.apache.commons.io.filefilter.FalseFileFilter.FALSE;
import org.apache.commons.io.filefilter.SizeFileFilter;
import static org.apache.commons.io.filefilter.TrueFileFilter.TRUE;

/**
 *
 * @author NCBS
 */
public class FixDataPurgingWorker extends BaseProcessor {
    
    public FixDataPurgingWorker(Map<String, ? extends Object> context) {
        super(context);
    }
    
    @Override
    protected boolean doExecute() throws Exception {
        getLogger().info("Start Purging");
        try {
            FixDataPurgingDao dataDao = new FixDataPurgingDao(session);
            FixDataPurging model = null;
            List<FixDataPurging> listModel = null;
            listModel = dataDao.list();
            getLogger().info("Total Purging : " + listModel.size());
            for (int i = 0; i < listModel.size(); i++) {
                model = listModel.get(i);
                getLogger().debug("Type Purging : " + model.getFlgtyp());
                if (model.getFlgtyp().equalsIgnoreCase("D")) {
                    getLogger().info("Purging Table : " + model.getDataloc());
                    doPurgingTable(model);
                } else if (model.getFlgtyp().equalsIgnoreCase("F")) {
                    getLogger().info("Purging Folder : " + model.getDataloc());
                    doPurgingFolder(model);
                } else if (model.getFlgtyp().equalsIgnoreCase("L")) {
                    getLogger().info("Purging File : " + model.getDataloc());
                    doPurgingFile(model);
                } else if (model.getFlgtyp().equalsIgnoreCase("S")) {
                    getLogger().info("Purging Table with subquery : " + model.getDataloc());
                    doPurgingTableSubQuery(model);
                } else if ("M".equalsIgnoreCase(model.getFlgtyp())) {
                    getLogger().info("Purging Table with multiple Query : " + model.getDataloc());
                    doPurgingTableMultiQuery(model);
                } else if ("H".equalsIgnoreCase(model.getFlgtyp())) {
                    getLogger().info("Purging Table move to History : " + model.getDataloc());
                    doHistoryTable(model);
                } else {
                    getLogger().info("Purging Method not Found for DataLoc : " + model.getDataloc());
                }
                //update last purging
                model.setLastsuccesspurging(SchedulerUtil.getTime());
                dataDao.update(model);
            }
        } catch (Exception e) {
            getLogger().error("Class Fix Purging Error");
            getLogger().error(e, e);
        }
        getLogger().info("End Purging");
        return true;
    }
    
    private void doPurgingTable(FixDataPurging model) throws Exception {
        String tableName = model.getDataloc();
        String fieldCondition = model.getFilepattern();
        double retention = model.getRetperiod();
        
        this.session.createSQLQuery("DELETE FROM " + tableName + " WHERE " + fieldCondition + " < TRUNC(SYSDATE) - " + String.valueOf(retention))
            .executeUpdate();
        this.commit();
    }
    
    private void doHistoryTable(FixDataPurging model) throws Exception {
        String tableName = model.getDataloc();
        String histTable = model.getHistTable();
        String fieldCondition = model.getFilepattern();
        double retention = model.getRetperiod();
        if(!"".equalsIgnoreCase(histTable)){
            this.session.createSQLQuery("INSERT INTO " + histTable + " SELECT * FROM " + tableName + " WHERE " + fieldCondition + " < TRUNC(SYSDATE) - " + String.valueOf(retention))
            .executeUpdate();
        this.commit();
            this.session.createSQLQuery("DELETE FROM " + tableName + " WHERE " + fieldCondition + " < TRUNC(SYSDATE) - " + String.valueOf(retention))
            .executeUpdate();
        this.commit();
        }
    }
    
    private void doPurgingTableSubQuery(FixDataPurging model) throws Exception {
        String tableName = model.getDataloc();
        String[] fieldCondition = model.getFilepattern().split(";");
        double retention = model.getRetperiod();
        
        this.session.createSQLQuery("DELETE FROM " + tableName + " WHERE " + fieldCondition[0] + " IN (SELECT " + fieldCondition[1] + " from " + fieldCondition[2] + " where " + fieldCondition[3] + " < TRUNC(SYSDATE) - " + String.valueOf(retention) + ")")
            .executeUpdate();
        this.commit();
    }

    private void doPurgingTableMultiQuery(FixDataPurging model) throws Exception {
        String tableName = model.getDataloc();
        List fieldCondition = bdsm.scheduler.util.SchedulerUtil.getParameter(model.getFilepattern(), ";");
        double retention = model.getRetperiod();
        int fieldCount = fieldCondition.size();

        String DateQuery = " TRUNC(SYSDATE) - " + String.valueOf(retention);
        String deletion = "DELETE FROM " + tableName;
        StringBuilder Query = new StringBuilder();
        Query.append(deletion);

        if (!fieldCondition.isEmpty()) {
            int flagize = 0;
            for (Object myQuery : fieldCondition) {
                List vallop = bdsm.scheduler.util.SchedulerUtil.getParameter(myQuery.toString(), "|");
                int singleFlag = 1;

                for (Object single : vallop) {
                    if (flagize == 0 && singleFlag == 1) {
                        Query.append(" WHERE ");
                    }
                    else {
                        if (singleFlag == 1) {
                            Query.append(single.toString().equalsIgnoreCase("1") ? " AND " : " OR ");
                        }
                        else {
                            if (fieldCount == 1 && vallop.size() == 1) {
                                Query.append(myQuery.toString()).append(" < ").append(DateQuery);
                            }
                            else {
                                if (singleFlag == 4 && StatusDefinition.datePurging.equalsIgnoreCase(single.toString())) {// value for query
                                    Query.append(DateQuery).append(" "); 
                                }
                                else {
                                    Query.append(single.toString()).append(" ");
                                }   
                            }
                        }
                    }
                    singleFlag++;
                }
                flagize++;
            }
            
            // assumption there will be no Query date, just Delete table itself
            if (flagize == 0) {
                Query.delete(0, Query.length());
                Query.append(deletion);
            }
            
            getLogger().info("QUERY :" + Query);
            
            this.session.createSQLQuery(Query.toString())
                .executeUpdate();
            this.commit();
        }
    }
    
    private void commit() {
        this.tx.commit();
        this.tx = this.session.beginTransaction();
    }

    private void doPurgingFolder(FixDataPurging model) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) (model.getRetperiod() * -(24 * 60)));
        Date thresoldDate = calendar.getTime();
        File tmpFile = null;
        Iterator<File> filesToDelete = FileUtils.iterateFiles(new File(model.getDataloc()), new AgeFileFilter(thresoldDate), TRUE);
        while (filesToDelete.hasNext()) {
            tmpFile = filesToDelete.next();
            if ("%".equalsIgnoreCase(model.getFilepattern()) && tmpFile.isDirectory()) {
                delete(tmpFile);
            } else if (tmpFile.getName().contains(model.getFilepattern()) && tmpFile.isDirectory()) {
                delete(tmpFile);
            }
        }
    }
    
    private void doPurgingFile(FixDataPurging model) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) (model.getRetperiod() * -(24 * 60)));
        Date thresoldDate = calendar.getTime();
        File tmpFile = null;
        
        AndFileFilter mainFilter = new AndFileFilter();
        mainFilter.addFileFilter(new AgeFileFilter(thresoldDate));
        mainFilter.addFileFilter((model.getFileSizeLessOrEqual()==null)? TRUE: new SizeFileFilter(model.getFileSizeLessOrEqual().longValue() + 1, false));
        
        Iterator<File> filesToDelete = FileUtils.iterateFiles(new File(model.getDataloc()), mainFilter, FALSE);
        while (filesToDelete.hasNext()) {
            tmpFile = filesToDelete.next();
            if ("%".equalsIgnoreCase(model.getFilepattern())) {
                tmpFile.delete();
            } else if (tmpFile.getName().contains(model.getFilepattern())) {
                tmpFile.delete();
            }
        }
    }
    
    public void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }
}
