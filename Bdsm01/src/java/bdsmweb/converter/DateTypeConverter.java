package bdsmweb.converter;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import bdsm.util.oracle.DateUtility;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("rawtypes")
public class DateTypeConverter extends org.apache.struts2.util.StrutsTypeConverter {
	
    /**
     * 
     * @param context
     * @param params
     * @param destClass
     * @return
     */
    @Override
	public Object convertFromString(Map context, String[] params, Class destClass) {
		String stDate = params[0];
		
		if (org.apache.commons.lang.StringUtils.isBlank(stDate))
			return null;
		
		boolean isMonthInDigit = stDate.matches("[\\d-/: ]+");
		Object result;
		
			if (isMonthInDigit) { // month in digit
				try {
					result = DateUtility.DATE_TIME_FORMAT_DDMMYYYY.parse(stDate.replaceAll("[/]", "-"));
				}
				catch (ParseException pe) {
					try {
						result = DateUtility.DATE_FORMAT_DDMMYYYY.parse(stDate.replaceAll("[/-]", ""));
					}
					catch (ParseException pe2) {
						throw new com.opensymphony.xwork2.conversion.TypeConversionException("Invalid Date Format", pe);
					}
				}
			}
			else { // month in 3 characters
				try {
					result = DateUtility.DATE_TIME_FORMAT_DDMMMYYYY.parse(stDate.replaceAll("[/-]", " "));
				}
				catch (ParseException pe) {
					try {
						result = DateUtility.DATE_FORMAT_DDMMMYYYY.parse(stDate.replaceAll("[/-]", " "));
					}
					catch (ParseException pe2) {
						throw new com.opensymphony.xwork2.conversion.TypeConversionException("Invalid Date Format", pe);
					}
				}
			}
			
			if (destClass.equals(Date.class))
				;
			else if (destClass.equals(Timestamp.class))
				result = new Timestamp(((Date) result).getTime());
			
			return result;
	}

    /**
     * 
     * @param context
     * @param sourceObject
     * @return
     */
    @Override
	public String convertToString(Map context, Object sourceObject) {
		/* You can use struts 2 date tag <s:date name="" format="" /> to customize formatting display */
		return DateUtility.DATE_FORMAT_DDMMMYYYY.format((Date) sourceObject);
	}

}
