package bdsm.model;

/**
 *
 * @author Jaka
 */
public enum ETaxBillingType {
    
    DJP(1), 
    DJBC(2), 
    DJA(3);
    
    private final int value;

    private ETaxBillingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static ETaxBillingType fromValue(int value) {
        for(ETaxBillingType _type : ETaxBillingType.values()) {
            if(value == _type.getValue()) {
                return _type;
            }
        }
        return null;
    }
    
    public static ETaxBillingType fromBillingIdPrefix(String billingId) {
        if(billingId != null) {
            if(billingId.matches("^[0123].*")) {
                return ETaxBillingType.DJP;
            } else if(billingId.matches("^[456].*")) {
                return ETaxBillingType.DJBC;
            } else if(billingId.matches("^[789].*")) {
                return ETaxBillingType.DJA;
            }
        }
        return null;
    }
}
