package bdsmhost.converter;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("rawtypes")
public class BigDecimalTypeConverter extends org.apache.struts2.util.StrutsTypeConverter {

    /**
     * 
     * @param context
     * @param params
     * @param destClass
     * @return
     */
    @Override
	public Object convertFromString(java.util.Map context, String[] params, Class destClass) {
		String stNumber = params[0];
		int idxComma, idxPoint;
		String stDecimal, stThousand;
		
		
		if (org.apache.commons.lang.StringUtils.isBlank(stNumber))
			return java.math.BigDecimal.ZERO;
		
		idxComma = stNumber.indexOf(",");
		idxPoint = stNumber.indexOf(".");
		stDecimal = stThousand = null;
		
		if ((idxComma == -1) && (idxPoint == -1));
		else {
			if (idxComma == -1) {
				stDecimal = ".";
			}
			else if (idxPoint == -1) {
				stDecimal = ",";
			}
			else {
				stDecimal = (idxComma > idxPoint)? ",": ".";
				stThousand = (idxComma > idxPoint)? ".": ",";
			}
			
			if (stThousand != null)
				stNumber = stNumber.replaceAll("[" + stThousand + " ]", "");
			
			if ((stDecimal != null) && (stDecimal.equals(",")))
				stNumber = stNumber.replace(',', '.');
		}
		
		return new java.math.BigDecimal(stNumber);
	}

    /**
     * 
     * @param context
     * @param sourceObject
     * @return
     */
    @Override
	public String convertToString(java.util.Map context, Object sourceObject) {
		return sourceObject.toString();
	}

}
