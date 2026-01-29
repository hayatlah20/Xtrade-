import java.util.ArrayList;
import java.util.List;

public class TradingPlatform {

    private List<Trader> traders = new ArrayList<>();
    private List<Asset> assets = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    public void addTrader(Trader trader) {
        traders.add(trader);
    }

    public void addAsset(Asset asset) {
        for (Asset a : assets) {
            if (a.getCode().equals(asset.getCode()))
                throw new IllegalArgumentException("Code actif déjà existant");
        }
        assets.add(asset);
    }

    public void showAssets() {
        for (Asset a : assets) {
            System.out.println(a.getCode() + " | " + a.getName() +
                    " | " + a.getUnitPrice() +
                    " | " + a.getClass().getSimpleName());
        }
    }

    public Trader getTraderById(int id) {
        for (Trader t : traders) {
            if (t.getId() == id)
                return t;
        }
        throw new IllegalArgumentException("Trader introuvable");
    }

    public Asset getAssetByCode(String code) {
        for (Asset a : assets) {
            if (a.getCode().equals(code))
                return a;
        }
        throw new IllegalArgumentException("Actif introuvable");
    }

    public void buyAsset(int traderId, String assetCode, int quantity) {
        Trader trader = getTraderById(traderId);
        Asset asset = getAssetByCode(assetCode);

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantité invalide");

        double cost = asset.getUnitPrice() * quantity;

        if (trader.getBalance() < cost)
            throw new IllegalArgumentException("Solde insuffisant");

        trader.debit(cost);
        trader.getPortfolio().addAsset(asset, quantity);

        transactions.add(new Transaction("BUY", asset, quantity));
    }

    public void sellAsset(int traderId, String assetCode, int quantity) {
        Trader trader = getTraderById(traderId);
        Asset asset = getAssetByCode(assetCode);

        trader.getPortfolio().removeAsset(asset, quantity);
        trader.credit(asset.getUnitPrice() * quantity);

        transactions.add(new Transaction("SELL", asset, quantity));
    }

    public void showTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}

