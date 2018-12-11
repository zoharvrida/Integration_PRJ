package bdsmhost.dao;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

import bdsm.model.BaseModel;
import bdsm.model.MfcUdMaster_LLD;
import bdsm.model.MfcUdMasterPK;


/**
 * @author v00019372
 */
public class LLDDAO extends BaseDao {
	protected final String PACKAGE_NAME = "PK_BDSM_VALAS_LLD";
	

	public LLDDAO(Session session) {
		super(session);
	}


	public BigDecimal getUSDRate() throws Exception {
		String query = "{ ? = call " + PACKAGE_NAME + ".lld_get_USD_rate }";
		CallableStatement stmt = ((SessionImpl) this.getSession()).connection().prepareCall(query);
		stmt.registerOutParameter(1, Types.NUMERIC);
		stmt.execute();
		
		BigDecimal result = stmt.getBigDecimal(1);
		stmt.close();
		
		return result;
	}


	protected BigDecimal getRate(Object param) throws Exception {
		String query = null;
		
		if (param instanceof Integer)
			query = "{ ? = call " + PACKAGE_NAME + ".lld_get_rate_by_cod_ccy(?) }";
		else if (param instanceof String)
			query = "{ ? = call " + PACKAGE_NAME + ".lld_get_rate_by_nam_ccy(?) }";
		
		CallableStatement stmt = ((SessionImpl) this.getSession()).connection().prepareCall(query);
		stmt.registerOutParameter(1, Types.NUMERIC);
		
		if (param instanceof Integer) {
			query = "{ ? = call " + PACKAGE_NAME + ".lld_get_rate_by_cod_ccy(?) }";
			stmt.setInt(2, (Integer) param);
		}
		else if (param instanceof String) {
			query = "{ ? = call " + PACKAGE_NAME + ".lld_get_rate_by_nam_ccy(?) }";
			stmt.setString(2, (String) param);
		}
		
		stmt.execute();
		
		BigDecimal result = stmt.getBigDecimal(1);
		stmt.close();
		
		return result;
	}

	public BigDecimal getRateByCodCcy(Integer codCcy) throws Exception {
		return this.getRate(codCcy);
	}

	public BigDecimal getRateByNamCcy(String namCcy) throws Exception {
		return this.getRate(namCcy);
	}


	protected BigDecimal getEquivUSDAmount_(BigDecimal txnAmount, Object param) throws Exception {
		String query = "{ ? = call " + PACKAGE_NAME + ".lld_get_equiv_USD_amount(?, ?) }";
		CallableStatement stmt = ((SessionImpl) this.getSession()).connection().prepareCall(query);
		stmt.registerOutParameter(1, Types.NUMERIC);
		stmt.setBigDecimal(2, txnAmount);
		if (param == null)
			stmt.setString(3, "IDR");
		else if (param instanceof Integer)
			stmt.setInt(3, (Integer) param);
		else if (param instanceof String)
			stmt.setString(3, (String) param);
		stmt.execute();
		
		BigDecimal result = stmt.getBigDecimal(1);
		stmt.close();
		
		return result;
	}

	public BigDecimal getEquivUSDAmount(BigDecimal txnAmount, Integer codCcy) throws Exception {
		return this.getEquivUSDAmount_(txnAmount, codCcy);
	}

	public BigDecimal getEquivUSDAmount(BigDecimal txnAmount, String namCcy) throws Exception {
		return this.getEquivUSDAmount_(txnAmount, namCcy);
	}

	public BigDecimal getEquivUSDAmount(BigDecimal localTxnAmount) throws Exception {
		return this.getEquivUSDAmount_(localTxnAmount, null);
	}


	protected List<MfcUdMaster_LLD> getAvailUd_(Integer codCust, String codAcctNo, BigDecimal txnAmt, Object param) throws Exception {
		List<MfcUdMaster_LLD> result = new java.util.ArrayList<MfcUdMaster_LLD>(0);
		
		String query = "";
		if (codCust != null) {
			if (param != null) // txnAmt in USD
				query = "{ ? = call " + PACKAGE_NAME + ".lld_get_avail_all_ud(?, ?, ?, ?, ?) }";
			else
				query = "{ ? = call " + PACKAGE_NAME + ".lld_get_avail_all_ud(?, ?, ?, ?) }";
		}
		else {
			if (param != null)
				query = "{ ? = call " + PACKAGE_NAME + ".lld_get_avail_all_ud(?, ?, ?, ?) }";
			else
				query = "{ ? = call " + PACKAGE_NAME + ".lld_get_avail_all_ud(?, ?, ?) }";
		}
		
		
		CallableStatement stmt = ((SessionImpl) this.getSession()).connection().prepareCall(query);
		int i = 1, iOut;
		
		stmt.registerOutParameter(i++, Types.NUMERIC);
		if (codCust != null)
			stmt.setInt(i++, codCust);
		stmt.setString(i++, codAcctNo);
		stmt.setBigDecimal(i++, txnAmt);
		
		if (param == null);
		else if (param instanceof Integer)
			stmt.setInt(i++, (Integer) param);
		else if (param instanceof String)
			stmt.setString(i++, (String) param);
		
		iOut = i;
		stmt.registerOutParameter(i++, OracleTypes.CURSOR);
		
		stmt.execute();
		int returnValue = stmt.getInt(1);
		ResultSet resultSet = (ResultSet) stmt.getObject(iOut);
		
		if (returnValue > 0)
			while (resultSet.next()) {
				MfcUdMaster_LLD model = new MfcUdMaster_LLD();
				model.setCompositeId(new MfcUdMasterPK());
				
				model.getCompositeId().setNoCif(resultSet.getInt("NOCIF"));
				model.getCompositeId().setNoUd(resultSet.getString("NOUD"));
				
				model.setDtExpiry(resultSet.getDate("DTEXPIRY"));
				model.setCcyUd(resultSet.getString("CCYUD"));
				model.setAmtLimit(doubleToBigDecimal(resultSet.getDouble("AMTLIMIT")));
				model.setAmtAvail(doubleToBigDecimal(resultSet.getDouble("AMTAVAIL")));
				model.setCdBranch(resultSet.getInt("CDBRANCH"));
				model.setStatus(resultSet.getString("STATUS"));
				model.setRemarks(resultSet.getString("REMARKS"));
				model.setAmtLimitUsd(doubleToBigDecimal(resultSet.getDouble("AMTLIMITUSD")));
				model.setAmtAvailUsd(doubleToBigDecimal(resultSet.getDouble("AMTAVAILUSD")));
				model.setRatFcyLim(doubleToBigDecimal(resultSet.getDouble("RATFCYLIM")));
				model.setRatUsdLim(doubleToBigDecimal(resultSet.getDouble("RATUSDLIM")));
				model.setUdTypeCategory((resultSet.getString("UD_TYPE_CATEGORY")));
				model.setUdTypeValue((resultSet.getString("UD_TYPE_VALUE")));
				model.setPayeeName(resultSet.getString("PAYEE_NAME"));
				model.setPayeeCountry(resultSet.getString("PAYEE_COUNTRY"));
				model.setTradingProduct(resultSet.getString("TRADING_PRODUCT"));
				model.setDtIssued(resultSet.getDate("DT_ISSUED"));
				
				result.add(model);
			}
		
		resultSet.close();
		stmt.close();
		
		return result;
	}

	public List<MfcUdMaster_LLD> getAvailUd(String codAcctNo, BigDecimal txnAmt, Integer codTxnCcy) throws Exception {
		return this.getAvailUd_(null, codAcctNo, txnAmt, codTxnCcy);
	}

	public List<MfcUdMaster_LLD> getAvailUd(String codAcctNo, BigDecimal txnAmt, String namTxnCcy) throws Exception {
		return this.getAvailUd_(null, codAcctNo, txnAmt, namTxnCcy);
	}

	public List<MfcUdMaster_LLD> getAvailUd(Integer codCust, String codAcctNo, BigDecimal txnAmt, Integer codTxnCcy) throws Exception {
		return this.getAvailUd_(codCust, codAcctNo, txnAmt, codTxnCcy);
	}

	public List<MfcUdMaster_LLD> getAvailUd(Integer codCust, String codAcctNo, BigDecimal txnAmt, String namTxnCcy) throws Exception {
		return this.getAvailUd_(codCust, codAcctNo, txnAmt, namTxnCcy);
	}


	protected Object[] listOfUd_(Object param) throws Exception {
		List<MfcUdMaster_LLD> lstUdMaster_LLD = new java.util.ArrayList<MfcUdMaster_LLD>(0);
		List<String> lstRelationAcctCust = new java.util.ArrayList<String>(0);
		
		String query = "{ ? = call " + PACKAGE_NAME + ".lld_list_of_all_ud(?, ?) }";
		CallableStatement stmt = ((SessionImpl) this.getSession()).connection().prepareCall(query);
		stmt.registerOutParameter(1, Types.NUMERIC);
		if (param instanceof Integer)
			stmt.setInt(2, (Integer) param);
		else
			stmt.setString(2, (String) param);
		stmt.registerOutParameter(3, OracleTypes.CURSOR);
		
		stmt.execute();
		int returnValue = stmt.getInt(1);
		ResultSet resultSet = (ResultSet) stmt.getObject(3);
		
		if (returnValue > 0)
			while (resultSet.next()) {
				MfcUdMaster_LLD model = new MfcUdMaster_LLD();
				model.setCompositeId(new MfcUdMasterPK());
				
				model.getCompositeId().setNoCif(resultSet.getInt("NOCIF"));
				model.getCompositeId().setNoUd(resultSet.getString("NOUD"));
				
				model.setDtExpiry(resultSet.getDate("DTEXPIRY"));
				model.setCcyUd(resultSet.getString("CCYUD"));
				model.setAmtLimit(doubleToBigDecimal(resultSet.getDouble("AMTLIMIT")));
				model.setAmtAvail(doubleToBigDecimal(resultSet.getDouble("AMTAVAIL")));
				model.setCdBranch(resultSet.getInt("CDBRANCH"));
				model.setStatus(resultSet.getString("STATUS"));
				model.setRemarks(resultSet.getString("REMARKS"));
				model.setAmtLimitUsd(doubleToBigDecimal(resultSet.getDouble("AMTLIMITUSD")));
				model.setAmtAvailUsd(doubleToBigDecimal(resultSet.getDouble("AMTAVAILUSD")));
				model.setRatFcyLim(doubleToBigDecimal(resultSet.getDouble("RATFCYLIM")));
				model.setRatUsdLim(doubleToBigDecimal(resultSet.getDouble("RATUSDLIM")));
				model.setUdTypeCategory((resultSet.getString("UD_TYPE_CATEGORY")));
				model.setUdTypeValue((resultSet.getString("UD_TYPE_VALUE")));
				model.setPayeeName(resultSet.getString("PAYEE_NAME"));
				model.setPayeeCountry(resultSet.getString("PAYEE_COUNTRY"));
				model.setTradingProduct(resultSet.getString("TRADING_PRODUCT"));
				model.setDtIssued(resultSet.getDate("DT_ISSUED"));
				
				lstUdMaster_LLD.add(model);
				lstRelationAcctCust.add(resultSet.getString("COD_ACCT_CUST_REL"));
			}
		
		resultSet.close();
		stmt.close();
		
		return new Object[] {lstUdMaster_LLD, lstRelationAcctCust};
	}

	@SuppressWarnings("unchecked")
	public List<MfcUdMaster_LLD> listOfUd(String codAcctNo) throws Exception {
		return (List<MfcUdMaster_LLD>) listOfUd_(codAcctNo)[0];
	}

	/**
	 * 
	 * @param codAcctNo
	 * @return [0] = List<MfcUdMaster_LLD>, 
	 *         [1] = List<String> --> Account Customer Relation, ex: SOW, JOF, JOO, JAF, JAO, .. etc
	 * @throws Exception
	 */
	public Object[] listOfUdWithAcctCustRelation(String codAcctNo) throws Exception {
		return listOfUd_(codAcctNo);
	}

	@SuppressWarnings("unchecked")
	public List<MfcUdMaster_LLD> listOfUd(Integer codCustNo) throws Exception {
		return (List<MfcUdMaster_LLD>) listOfUd_(codCustNo)[0];
	}
	
	public Object[] listOfUdWithAcctCustRelation(Integer codCustNo) throws Exception {
		return listOfUd_(codCustNo);
	}


	private BigDecimal doubleToBigDecimal(double d) {
		return new BigDecimal(Double.valueOf(d).toString());
	}



	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
