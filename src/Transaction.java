import java.time.LocalDateTime;

public class Transaction {
    private int traderId;
    private String type;
    private Asset asset;
    private int quantity;
    private double amount;
    private LocalDateTime date;

    public Transaction(int traderId, String type, Asset asset, int quantity) {
        this.traderId = traderId;
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.amount = asset.getUnitPrice() * quantity;
        this.date = LocalDateTime.now();
    }

    public int getTraderId() {
        return traderId;
    }

    public String getType() {
        return type;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + " | Trader " + traderId +
                " | " + type +
                " | " + asset.getCode() +
                " | Qty: " + quantity +
                " | Montant: " + amount;
    }
}


