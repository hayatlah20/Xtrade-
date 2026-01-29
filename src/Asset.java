public abstract class Asset {
    protected String code;
    protected String name;
    protected double unitPrice;

    public Asset(String code, String name, double unitPrice) {
        if (unitPrice <= 0)
            throw new IllegalArgumentException("Prix invalide");
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
}

