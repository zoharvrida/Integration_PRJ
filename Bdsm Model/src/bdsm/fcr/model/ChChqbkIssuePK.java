/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;


/**
 *
 * @author NCBS
 */
public class ChChqbkIssuePK implements java.io.Serializable{
	private String codAcct;
	private String flgMntStatus;
	private Integer codEntityVPD;
	private String chqkStart;
	private String chqkEnd;

	/**
	 * @return the codAcct
	 */
	public String getCodAcct() {
		return codAcct;
	}

	/**
	 * @param codAcct the codAcct to set
	 */
	public void setCodAcct(String codAcct) {
		this.codAcct = this.rightPad(codAcct, 16, ' ');
	}

	/**
	 * @return the flgMntStatus
	 */
	public String getFlgMntStatus() {
		return flgMntStatus;
	}

	/**
	 * @param flgMntStatus the flgMntStatus to set
	 */
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}

	/**
	 * @return the codEntityVPD
	 */
	public Integer getCodEntityVPD() {
		return codEntityVPD;
	}

	/**
	 * @param codEntityVPD the codEntityVPD to set
	 */
	public void setCodEntityVPD(Integer codEntityVPD) {
		this.codEntityVPD = codEntityVPD;
	}

	/**
	 * @return the chqkStart
	 */
	public String getChqkStart() {
		return chqkStart;
	}

	/**
	 * @param chqkStart the chqkStart to set
	 */
	public void setChqkStart(String chqkStart) {
		this.chqkStart = this.leftPad(chqkStart, 12, '0');
	}

	/**
	 * @return the chqkEnd
	 */
	public String getChqkEnd() {
		return chqkEnd;
	}

	/**
	 * @param chqkEnd the chqkEnd to set
	 */
	public void setChqkEnd(String chqkEnd) {
		this.chqkEnd = this.leftPad(chqkEnd, 12, '0');
	}
	
	
	
	protected String leftPad(String input, int length, Character char_) {
		String result = input;
		
		if (length > input.length())
			result = this.repeatCharacter(char_, (length - input.length())) + result;
		
		return result; 
	}
	
	protected String rightPad(String input, int length, Character char_) {
		String result = input;
		
		if (length > input.length())
			result = result + this.repeatCharacter(char_, (length - input.length()));
		
		return result;
	}
	
	protected String repeatCharacter(Character char_, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<length; i++)
			sb.append(char_);
		
		return sb.toString();
	}
}
