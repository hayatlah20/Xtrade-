import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private Asset asset;
    private int quantity;
    private double price;
    private LocalDateTime date;

    public Transaction(String type, Asset asset, int quantity) {
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.price = asset.getUnitPrice();
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return type + " | " + asset.getName() +
                " | Qty: " + quantity +
                " | Prix: " + price +
                " | Date: " + date;
    }
}

