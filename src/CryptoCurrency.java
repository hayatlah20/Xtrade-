public class CryptoCurrency extends Asset {
    private String blockchain;

    public CryptoCurrency(String code, String name, double unitPrice, String blockchain) {
        super(code, name, unitPrice);
        this.blockchain = blockchain;
    }

    public String getBlockchain() { return blockchain; }
}

