package bdsm.util.oracle;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * @author v00019372
 */
public class DateUtility {
	public static final DateFormat DATE_FORMAT_DDMMYYYY = new SimpleDateFormat("ddMMyyyy");
	public static final DateFormat DATE_FORMAT_DDMMMYYYY = new SimpleDateFormat("dd MMM yyyy");
	public static final DateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public static final DateFormat DATE_FORMAT_YYMMDD = new SimpleDateFormat("yyMMdd");
	public static final DateFormat TIME_FORMAT_NO_COLON = new SimpleDateFormat("HHmmss");
	public static final DateFormat TIME_FORMAT_COLON = new SimpleDateFormat("HH:mm:ss");
	public static final DateFormat TIME_FORMAT_COLON24 = new SimpleDateFormat("HH24:mm:ss");
    public static final DateFormat DATE_TIME_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATE_TIME_FORMAT_DDMMYYYY = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	public static final DateFormat DATE_TIME_FORMAT_DDMMMYYYY = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	public static final DateFormat DATE_TIME_SLASH_DDMMMYYYY = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final DateFormat DATE_TIME_SLASH_MMMDDYYYY = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final long SECOND = 1000;
	private static final long MINUTE = 60 * SECOND;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	
	
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:default:connection");
	}
	
	private static String getClearingBranchCalendar(Connection connection, int clearingBranchCode, int year, int month) throws SQLException {
		if (connection == null) 
			connection = getConnection();
		
		PreparedStatement ps;
		ResultSet rs = null;
		StringBuffer SQL;
		String days = null;
		
		SQL = new StringBuffer("SELECT flg_month_days ")
			.append("FROM ba_endpoint_cldr ")
			.append("WHERE flg_mnt_status = 'A' ")
			.append("	AND cod_clg_brn = ( SELECT cod_clg_brn FROM st_clgbrn_xref WHERE cod_cc_brn = ? ) ")
			.append("	AND ctr_cldr_year = ? ")
			.append("	AND ctr_cldr_month = ? ")
			;
		ps = connection.prepareStatement(SQL.toString());
		ps.setInt(1, clearingBranchCode);
		ps.setInt(2, year);
		ps.setInt(3, month);
		
		try {
			rs = ps.executeQuery();
			if (rs.next())
				days = rs.getString(1);
		}
		catch (Exception e) {}
		finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		
		return days;
	}
	
	public static Date getClearingBranchNextWorkingDate(int clearingBranchCode, Date date) throws SQLException {
		return getClearingBranchNextWorkingDate(null, clearingBranchCode, date);
	}
	
	public static Date getClearingBranchNextWorkingDate(Connection connection, int clearingBranchCode, Date date) throws SQLException {
		Calendar cldr;
		int year, month, day;
		int i = 1;
		String days;
		boolean found = false;
		
		cldr = Calendar.getInstance();
		cldr.setTime(date);
		
		do {
			cldr.add(Calendar.DAY_OF_MONTH, 1); // get next day
			year = cldr.get(Calendar.YEAR);
			month = cldr.get(Calendar.MONTH) + 1;
			day = cldr.get(Calendar.DAY_OF_MONTH);
			
			days = getClearingBranchCalendar(connection, clearingBranchCode, year, month);
			if (days == null)
				break;
			
			for (i=day; i<=days.length(); i++)
				if (found = (days.charAt(i-1) == '1'))
					break;
			
			if (!found)
				cldr.set(Calendar.DAY_OF_MONTH, (i-1));
		}
		while (!found);
		
		if (found) {
			cldr.set(Calendar.DAY_OF_MONTH, i);
			return cldr.getTime();
		}
		else
			return null;
	}
	
	
	public static String getClearingBranchNextWorkingDate(int clearingBranchCode, String date, int type) throws SQLException, ParseException {
		return getClearingBranchNextWorkingDate(null, clearingBranchCode, date, type);
	}
	
	public static String getClearingBranchNextWorkingDate(Connection connection, int clearingBranchCode, String date, int type) throws SQLException, ParseException {
		Date dtDate;
		
		if (type == 1)
			dtDate = DATE_FORMAT_DDMMYYYY.parse(date);
		else
			dtDate = DATE_FORMAT_YYYYMMDD.parse(date);
		
		dtDate = getClearingBranchNextWorkingDate(connection, clearingBranchCode, dtDate);
		
		if (dtDate == null)
			return " ";
		else if (type == 1)
			return DATE_FORMAT_DDMMYYYY.format(dtDate);
		else
			return DATE_FORMAT_YYYYMMDD.format(dtDate);
	}
	
	public static final int[] getDateDiff(Date date1, Date date2) {
		int[] result = new int[4]; /* [days, hours, minutes, seconds] */
		long diff = date2.getTime() - date1.getTime();
		long major, minor;
		
		// DAYS
		major = diff / DAY;
		minor = diff % DAY;
		result[0] = (int) major;
		
		// HOURS
		diff = minor;
		major = diff / HOUR;
		minor = diff % HOUR;
		result[1] = (int) major;
		
		// MINUTES
		diff = minor;
		major = diff / MINUTE;
		minor = diff % MINUTE;
		result[2] = (int) major;
		
		// SECONDS
		diff = minor;
		major = diff / SECOND;
		minor = diff % SECOND;
		result[3] = (int) major;
		
		return result;
	}
	
	public static final Date getLastDayOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		return cal.getTime();
	}
	
}
