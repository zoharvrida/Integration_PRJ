package bdsm.service;


import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import bdsm.model.Parameter;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.TmpParameterUploadDAO;
import bdsm.scheduler.model.TmpParameterUpload;
import bdsmhost.dao.ParameterDao;


/**
 * 
 * @author v00019372
 */
public class ParameterUploadService {
	private static final Logger logger = Logger.getLogger(ParameterUploadService.class);
	private static final java.text.DateFormat dateTimeFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
	private TmpParameterUploadDAO tmpParameterUploadDAO;
	private ParameterDao parameterDAO;
	
	
	/* == Setter Injection == */
	
	public void setTmpParameterUploadDAO(TmpParameterUploadDAO tmpParameterUploadDAO) {
		this.tmpParameterUploadDAO = tmpParameterUploadDAO;
	}
	
	public void setParameterDAO(ParameterDao parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	
	
	public void runValidate(String batchNo) {
		Date startTime = logStartTime("runValidate");
		
		List<TmpParameterUpload> dataList = this.tmpParameterUploadDAO.getByBatchNoAndFlagStatus(batchNo, "null");
		Set<String> setDuplicate = new java.util.HashSet<String>(); 
		Parameter parameter;
		String oldValue, newValue;
		
		if (dataList.size() > 0) {
			for (TmpParameterUpload tpu : dataList) {
				oldValue = newValue = null;
				
				parameter = this.parameterDAO.get(tpu.getCodeParam());
				if (parameter == null) {
					this.setError(tpu, "Code Param \"" + tpu.getCodeParam() + "\" Not Found!!!");
					continue;
				}
				
				if (this.tmpParameterUploadDAO.getByBatchNoAndCodeParam(tpu.getBatchNo(), tpu.getCodeParam()).size() > 1) {
					if (setDuplicate.contains(tpu.getCodeParam())) {
						setError(tpu, "Duplicate data entries \"" + tpu.getCodeParam() + "\"!!!");
						continue;
					}
					else
						setDuplicate.add(tpu.getCodeParam());
				}
				
				if (StringUtils.isBlank(tpu.getValue()))
					tpu.setValue(null);
				
				
				if ("S".equals(parameter.getTypParam())) {
					if ("O|D|A".indexOf(tpu.getCommand()) == -1) {
						this.setError(tpu, "Command \"" + tpu.getCommand() + "\" is not Recognized for String Valued Parameter!!!");
						continue;
					}
					
					oldValue = parameter.getStrVal();
					
					if (tpu.getValue() != null) {
						/* eliminate front and end character ';' if exist */
						if (tpu.getValue().charAt(0) == ';')
							tpu.setValue(tpu.getValue().substring(1));
						if (tpu.getValue().endsWith(";"))
							tpu.setValue(tpu.getValue().substring(0, tpu.getValue().length()-1));
					}
					
					if ("O".equals(tpu.getCommand()))
						newValue = tpu.getValue();
					else if ("D".equals(tpu.getCommand())) {
						StringBuilder sb = new StringBuilder((oldValue==null)? "": oldValue);
						sb.insert(0, ";"); sb.append(";");
						
						StringTokenizer st = new StringTokenizer((tpu.getValue()==null)? "": tpu.getValue(), ";");
						String strSearch;
						while(st.hasMoreElements()) {
							strSearch = ";" + st.nextElement() + ";";
							
							int pos = sb.indexOf(strSearch);
							if (pos > -1)
								sb.delete(pos + 1, pos + strSearch.length());
						}
						
						if (sb.charAt(0) == ';') sb.deleteCharAt(0);
						if (sb.charAt(sb.length() - 1) == ';') sb.deleteCharAt(sb.length() - 1);
						
						newValue = sb.toString();
					}
					else if ("A".equals(tpu.getCommand())) {
						StringBuilder sb = new StringBuilder((oldValue==null)? "": oldValue);
						sb.insert(0, ";"); sb.append(";");
						
						StringTokenizer st = new StringTokenizer((tpu.getValue()==null)? "": tpu.getValue(), ";");
						String strSearch;
						while(st.hasMoreElements()) {
							strSearch = ";" + st.nextElement() + ";";
							
							int pos = sb.indexOf(strSearch);
							if (pos == -1)
								sb.append(strSearch.substring(1));
						}
						
						if (sb.charAt(0) == ';') sb.deleteCharAt(0);
						if (sb.charAt(sb.length() - 1) == ';') sb.deleteCharAt(sb.length() - 1);
						
						newValue = sb.toString();
					}
				}
				else if ("N".equals(parameter.getTypParam())) {
					if ("O".indexOf(tpu.getCommand()) == -1) {
						this.setError(tpu, "Command \"" + tpu.getCommand() + "\" is not Recognized for Numeric Valued Parameter!!!");
						continue;
					}
					
					try {
						if (tpu.getValue() != null)
							Integer.valueOf(tpu.getValue());
					}
					catch (NumberFormatException nfe) {
						this.setError(tpu, "\"" + tpu.getValue() + "\" is not a Number!!!");
						continue;
					}
					
					oldValue = (parameter.getValue()!=null)? String.valueOf(parameter.getValue()): null;
					newValue = (tpu.getValue() != null)? tpu.getValue(): "0";
				}
				
				tpu.setOldValue(oldValue);
				tpu.setNewValue(newValue);
				tpu.setFlagStatus(StatusDefinition.UNAUTHORIZED);
				tpu.setStatusReason("Ready for Approve");
				tpu.setTypeParameter(parameter.getTypParam());
			}
		}
		
		logEndTime("runValidate", startTime);
	}
	
	public void runProcess(String batchNo) {
		Date startTime = logStartTime("runProcess");
		Parameter parameter;
		
		List<TmpParameterUpload> dataList = this.tmpParameterUploadDAO.getByBatchNoAndFlagStatus(batchNo, StatusDefinition.UNAUTHORIZED);
		if (dataList.size() > 0)
			for (TmpParameterUpload tpu : dataList) {
				parameter = this.parameterDAO.get(tpu.getCodeParam());
				
				if ("S".equals(tpu.getTypeParameter()))
					parameter.setStrVal(tpu.getNewValue());
				else if ("N".equals(tpu.getTypeParameter()))
					parameter.setValue(Integer.valueOf(tpu.getNewValue()));
				
				tpu.setFlagStatus(StatusDefinition.DONE);
				tpu.setStatusReason("Done");
			}
		
		logEndTime("runProcess", startTime);
	}
	
	public void runReject(String batchNo) {
		Date startTime = logStartTime("runReject");
		
		List<TmpParameterUpload> dataList = this.tmpParameterUploadDAO.getByBatchNoAndFlagStatus(batchNo, StatusDefinition.UNAUTHORIZED);
		if (dataList.size() > 0)
			for (TmpParameterUpload tpu : dataList)
				this.setError(tpu, "Rejected by Supervisor");
		
		logEndTime("runReject", startTime);
	}
	
	
	protected void setError(TmpParameterUpload object, String reason) {
		object.setFlagStatus(StatusDefinition.REJECTED);
		object.setStatusReason(reason);
	}
	
	protected static Date logStartTime(String methodName) {
		Date startTime = new Date();
		logger.debug("[BEGIN] " + methodName + " at : " + dateTimeFormatter.format(startTime));
		
		return startTime;
	}
	
	protected static void logEndTime(String methodName, Date startTime) {
		Date endTime = new Date();
		logger.debug("[END] " + methodName + " at : " + dateTimeFormatter.format(endTime) 
				+ " [" + (endTime.getTime() - startTime.getTime()) + " ms]");
	}

}
