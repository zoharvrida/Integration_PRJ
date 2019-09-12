package bdsm.model;

/**
 *
 * @author Jaka
 */
public enum ETaxPaymentType {
    
    CASH(1, "Setoran tunai"),
    DEBET_CASA(2, "Db CASA"),
    CEK_BG(3, "Cek/BG");
    
    final int value;
    final String desc;

    private ETaxPaymentType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    
    public static ETaxPaymentType fromValue(int value) {
        for (ETaxPaymentType at : ETaxPaymentType.values()) {
            if (at.getValue() == value) {
                return at;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc;
    }
    
}
