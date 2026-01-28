public class Stock extends Asset {
    private String market;

    public Stock(String code, String name, double unitPrice, String market) {
        super(code, name, unitPrice);
        this.market = market;
    }

    public String getMarket() { return market; }
}

