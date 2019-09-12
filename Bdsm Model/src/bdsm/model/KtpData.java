/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KtpData implements Serializable, Comparable<KtpData> {

    static final private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    static final private SimpleDateFormat dMMMyyyy = new SimpleDateFormat("d-MMM-yyyy");
    static final private String RESPON = "RESPON";
    static final private String AGAMA = "AGAMA";
    static final private String ALAMAT = "ALAMAT";
    static final private String DUSUN = "DUSUN";
    static final private String EKTP_STATUS = "EKTP_STATUS";
    static final private String GOL_DARAH = "GOL_DARAH";
    static final private String JENIS_KLMIN = "JENIS_KLMIN";
    static final private String JENIS_PKRJN = "JENIS_PKRJN";
    static final private String KAB_NAME = "KAB_NAME";
    static final private String KEC_NAME = "KEC_NAME";
    static final private String KEL_NAME = "KEL_NAME";
    static final private String KODE_POS = "KODE_POS";
    static final private String NAMA_LGKP = "NAMA_LGKP";
    static final private String NAMA_LGKP_AYAH = "NAMA_LGKP_AYAH";
    static final private String NAMA_LGKP_IBU = "NAMA_LGKP_IBU";
    static final private String NIK = "NIK";
    static final private String NO_KAB = "NO_KAB";
    static final private String NO_KEC = "NO_KEC";
    static final private String NO_KEL = "NO_KEL";
    static final private String NO_KK = "NO_KK";
    static final private String NO_PROP = "NO_PROP";
    static final private String NO_RT = "NO_RT";
    static final private String NO_RW = "NO_RW";
    static final private String PDDK_AKH = "PDDK_AKH";
    static final private String PROP_NAME = "PROP_NAME";
    static final private String STATUS_KAWIN = "STATUS_KAWIN";
    static final private String TGL_LHR = "TGL_LHR";
    static final private String TMPT_LHR = "TMPT_LHR";
    static final private String STAT_HBKEL = "STAT_HBKEL";
    static final private String PNYDNG_CCT = "PNYDNG_CCT";
    private String nik;
    private String responseMessage;
    private String name;
    private String birthPlace;
    private String dob;
    private String sex;
    private String bloodType;
    private String address;
    private String rt;
    private String rw;
    private String dusun;
    private int noKel;
    private String namKel;
    private int noKec;
    private String namKec;
    private int noKab;
    private String namKab;
    private int noProp;
    private String namProp;
    private String religion;
    private String posCode;
    private String marStat;
    private String profession;
    private String ektpStatus;
    private String lastEdu;
    private String namFather;
    private String namMother;
    private String nokk;
    private String stHubKel;
    private String penyCacat;
    private int rank;

    public KtpData() {
    }

    public void read(Map map) {
        Long l = null;
        this.setResponseMessage((String) map.get(RESPON));
        l = (Long) map.get(NIK);
        if (l != null) {
            this.setNik(l.toString());
        }
        this.setName((String) map.get(NAMA_LGKP));
        this.setBirthPlace((String) map.get(TMPT_LHR));
        this.setDob((String) map.get(TGL_LHR));
        this.setSex((String) map.get(JENIS_KLMIN));
        this.setBloodType((String) map.get(GOL_DARAH));
        this.setReligion((String) map.get(AGAMA));
        this.setAddress((String) map.get(ALAMAT));
        l = (Long) map.get(NO_RT);
        if (l != null) {
            this.setRt(String.format("%03d", l));
        }
        l = (Long) map.get(NO_RW);
        if (l != null) {
            this.setRw(String.format("%03d", l));
        }
        this.setDusun((String) map.get(DUSUN));
        l = (Long) map.get(NO_KEL);
        if (l != null) {
            this.setNoKel(l.intValue());
        }
        l = (Long) map.get(NO_KEC);
        if (l != null) {
            this.setNoKec(l.intValue());
        }
        l = (Long) map.get(NO_KAB);
        if (l != null) {
            this.setNoKab(l.intValue());
        }
        l = (Long) map.get(NO_PROP);
        if (l != null) {
            this.setNoProp(l.intValue());
        }
        this.setNamKel((String) map.get(KEL_NAME));
        this.setNamKec((String) map.get(KEC_NAME));
        this.setNamKab((String) map.get(KAB_NAME));
        this.setNamProp((String) map.get(PROP_NAME));
        l = (Long) map.get(KODE_POS);
        if (l != null) {
            this.setPosCode(l.toString());
        }
        this.setMarStat((String) map.get(STATUS_KAWIN));
        this.setProfession((String) map.get(JENIS_PKRJN));
        this.setEktpStatus((String) map.get(EKTP_STATUS));
        this.setLastEdu((String) map.get(PDDK_AKH));
        this.setNamFather((String) map.get(NAMA_LGKP_AYAH));
        this.setNamMother((String) map.get(NAMA_LGKP_IBU));
        l = (Long) map.get(NO_KK);
        if (l != null) {
            this.setNokk(l.toString());
        }

        this.setStHubKel((String) map.get(STAT_HBKEL));
        this.setPenyCacat((String) map.get(PNYDNG_CCT));
    }

    /**
     * @return the nik
     */
    public String getNik() {
        return nik;
    }

    /**
     * @param nik the nik to set
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        Date dt = null;
        try {
            dt = yyyyMMdd.parse(dob);
        } catch (Exception e) {
        }
        if (dt == null) {
            try {
                dt = dMMMyyyy.parse(dob);
            } catch (Exception e) {
            }
            if (dt != null) {
                this.dob = yyyyMMdd.format(dt);
            }
        } else {
            this.dob = dMMMyyyy.format(dt);
        }
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the bloodType
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * @param bloodType the bloodType to set
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the rt
     */
    public String getRt() {
        return rt;
    }

    /**
     * @param rt the rt to set
     */
    public void setRt(String rt) {
        this.rt = rt;
    }

    /**
     * @return the rw
     */
    public String getRw() {
        return rw;
    }

    /**
     * @param rw the rw to set
     */
    public void setRw(String rw) {
        this.rw = rw;
    }

    /**
     * @return the dusun
     */
    public String getDusun() {
        return dusun;
    }

    /**
     * @param dusun the dusun to set
     */
    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    /**
     * @return the noKel
     */
    public int getNoKel() {
        return noKel;
    }

    /**
     * @param noKel the noKel to set
     */
    public void setNoKel(int noKel) {
        this.noKel = noKel;
    }

    /**
     * @return the namKel
     */
    public String getNamKel() {
        return namKel;
    }

    /**
     * @param namKel the namKel to set
     */
    public void setNamKel(String namKel) {
        this.namKel = namKel;
    }

    /**
     * @return the noKec
     */
    public int getNoKec() {
        return noKec;
    }

    /**
     * @param noKec the noKec to set
     */
    public void setNoKec(int noKec) {
        this.noKec = noKec;
    }

    /**
     * @return the namKec
     */
    public String getNamKec() {
        return namKec;
    }

    /**
     * @param namKec the namKec to set
     */
    public void setNamKec(String namKec) {
        this.namKec = namKec;
    }

    /**
     * @return the noKab
     */
    public int getNoKab() {
        return noKab;
    }

    /**
     * @param noKab the noKab to set
     */
    public void setNoKab(int noKab) {
        this.noKab = noKab;
    }

    /**
     * @return the namKab
     */
    public String getNamKab() {
        return namKab;
    }

    /**
     * @param namKab the namKab to set
     */
    public void setNamKab(String namKab) {
        this.namKab = namKab;
    }

    /**
     * @return the noProp
     */
    public int getNoProp() {
        return noProp;
    }

    /**
     * @param noProp the noProp to set
     */
    public void setNoProp(int noProp) {
        this.noProp = noProp;
    }

    /**
     * @return the namProp
     */
    public String getNamProp() {
        return namProp;
    }

    /**
     * @param namProp the namProp to set
     */
    public void setNamProp(String namProp) {
        this.namProp = namProp;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return the posCode
     */
    public String getPosCode() {
        return posCode;
    }

    /**
     * @param posCode the posCode to set
     */
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    /**
     * @return the marStat
     */
    public String getMarStat() {
        return marStat;
    }

    /**
     * @param marStat the marStat to set
     */
    public void setMarStat(String marStat) {
        this.marStat = marStat;
    }

    /**
     * @return the profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     * @param profession the profession to set
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @return the ektpStatus
     */
    public String getEktpStatus() {
        return ektpStatus;
    }

    /**
     * @param ektpStatus the ektpStatus to set
     */
    public void setEktpStatus(String ektpStatus) {
        this.ektpStatus = ektpStatus;
    }

    /**
     * @return the lastEdu
     */
    public String getLastEdu() {
        return lastEdu;
    }

    /**
     * @param lastEdu the lastEdu to set
     */
    public void setLastEdu(String lastEdu) {
        this.lastEdu = lastEdu;
    }

    /**
     * @return the namFather
     */
    public String getNamFather() {
        return namFather;
    }

    /**
     * @param namFather the namFather to set
     */
    public void setNamFather(String namFather) {
        this.namFather = namFather;
    }

    /**
     * @return the namMother
     */
    public String getNamMother() {
        return namMother;
    }

    /**
     * @param namMother the namMother to set
     */
    public void setNamMother(String namMother) {
        this.namMother = namMother;
    }

    /**
     * @return the nokk
     */
    public String getNokk() {
        return nokk;
    }

    /**
     * @param nokk the nokk to set
     */
    public void setNokk(String nokk) {
        this.nokk = nokk;
    }

    /**
     * @return the stHubKel
     */
    public String getStHubKel() {
        return stHubKel;
    }

    /**
     * @param stHubKel the stHubKel to set
     */
    public void setStHubKel(String stHubKel) {
        this.stHubKel = stHubKel;
    }

    /**
     * @return the penyCacat
     */
    public String getPenyCacat() {
        return penyCacat;
    }

    /**
     * @param penyCacat the penyCacat to set
     */
    public void setPenyCacat(String penyCacat) {
        this.penyCacat = penyCacat;
    }

    @Override
    public String toString() {
        return "KtpData{" + "nik=" + nik + ", responseMessage=" + responseMessage + ", name=" + name + ", birthPlace=" + birthPlace + ", dob=" + dob + ", sex=" + sex + ", bloodType=" + bloodType + ", address=" + address + ", rt=" + rt + ", rw=" + rw + ", dusun=" + dusun + ", noKel=" + noKel + ", namKel=" + namKel + ", noKec=" + noKec + ", namKec=" + namKec + ", noKab=" + noKab + ", namKab=" + namKab + ", noProp=" + noProp + ", namProp=" + namProp + ", religion=" + religion + ", posCode=" + posCode + ", marStat=" + marStat + ", profession=" + profession + ", ektpStatus=" + ektpStatus + ", lastEdu=" + lastEdu + ", namFather=" + namFather + ", namMother=" + namMother + ", nokk=" + nokk + ", stHubKel=" + stHubKel + ", penyCacat=" + penyCacat + '}';
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int compareTo(KtpData o) {
        int compareRank = o.getRank();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (compareRank == this.rank) {
            try {
                if (o.getDob() != null) {
                    synchronized (sdf) {
                        if (sdf.parse(this.dob).compareTo(sdf.parse(o.getDob())) == -1) {
                            return -1;
                        } else if (sdf.parse(this.dob).compareTo(sdf.parse(o.getDob())) == 1) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(KtpData.class.getName()).log(Level.SEVERE, null, ex);
                return this.rank;
            }
        }
        return this.rank - compareRank;
    }
}
