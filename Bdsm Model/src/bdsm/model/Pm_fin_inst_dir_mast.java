/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019722
 */
public class Pm_fin_inst_dir_mast extends BaseModel {
    private String Cod_bank;
    private String Cod_Sector;
    private String Bi_Off_code;
    private String cod_province;
	private Integer cod_province_N;
    private String cod_city;
	private Integer cod_city_N;
    private String cod_fin_inst_id;

    /**
     * @return the Cod_bank
     */
    public String getCod_bank() {
        return Cod_bank;
    }

    /**
     * @param Cod_bank the Cod_bank to set
     */
    public void setCod_bank(String Cod_bank) {
        this.Cod_bank = Cod_bank;
    }

    /**
     * @return the Cod_Sector
     */
    public String getCod_Sector() {
        return Cod_Sector;
    }

    /**
     * @param Cod_Sector the Cod_Sector to set
     */
    public void setCod_Sector(String Cod_Sector) {
        this.Cod_Sector = Cod_Sector;
    }

    /**
     * @return the Bi_Off_code
     */
    public String getBi_Off_code() {
        return Bi_Off_code;
    }

    /**
     * @param Bi_Off_code the Bi_Off_code to set
     */
    public void setBi_Off_code(String Bi_Off_code) {
        this.Bi_Off_code = Bi_Off_code;
    }

    /**
     * @return the cod_province
     */
    public String getCod_province() {
        return cod_province;
    }

    /**
     * @param cod_province the cod_province to set
     */
    public void setCod_province(String cod_province) {
        this.cod_province = cod_province;
    }

    /**
     * @return the cod_city
     */
    public String getCod_city() {
        return cod_city;
    }

    /**
     * @param cod_city the cod_city to set
     */
    public void setCod_city(String cod_city) {
        this.cod_city = cod_city;
    }

    /**
     * @return the cod_fin_inst_id
     */
    public String getCod_fin_inst_id() {
        return cod_fin_inst_id;
    }

    /**
     * @param cod_fin_inst_id the cod_fin_inst_id to set
     */
    public void setCod_fin_inst_id(String cod_fin_inst_id) {
        this.cod_fin_inst_id = cod_fin_inst_id;
    }

	/**
	 * @return the cod_province_N
	 */
	public Integer getCod_province_N() {
		return cod_province_N;
	}

	/**
	 * @param cod_province_N the cod_province_N to set
	 */
	public void setCod_province_N(Integer cod_province_N) {
		this.cod_province_N = cod_province_N;
	}

	/**
	 * @return the cod_city_N
	 */
	public Integer getCod_city_N() {
		return cod_city_N;
	}

	/**
	 * @param cod_city_N the cod_city_N to set
	 */
	public void setCod_city_N(Integer cod_city_N) {
		this.cod_city_N = cod_city_N;
	}
}
