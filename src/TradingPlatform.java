import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

        transactions.add(new Transaction(traderId, "BUY", asset, quantity));
    }

    public void sellAsset(int traderId, String assetCode, int quantity) {
        Trader trader = getTraderById(traderId);
        Asset asset = getAssetByCode(assetCode);

        trader.getPortfolio().removeAsset(asset, quantity);
        trader.credit(asset.getUnitPrice() * quantity);

        transactions.add(new Transaction(traderId, "SELL", asset, quantity));
    }


    public void showTransactions() {
        transactions.forEach(System.out::println);
    }


    //  Toutes les transactions d’un trader donné
    public List<Transaction> getTransactionsByTrader(int traderId) {
        return transactions.stream()
                .filter(t -> t.getTraderId() == traderId)
                .toList();
    }

    //  Filtrer par type
    public List<Transaction> getTransactionsByType(String type) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .toList();
    }

    //  Filtrer par actif
    public List<Transaction> getTransactionsByAsset(String assetCode) {
        return transactions.stream()
                .filter(t -> t.getAsset().getCode().equalsIgnoreCase(assetCode))
                .toList();
    }

    //  Filtrer par intervalle de dates
    public List<Transaction> getTransactionsByDate(LocalDateTime start, LocalDateTime end) {
        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .toList();
    }

    //  Trier par date
    public List<Transaction> sortTransactionsByDate() {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .toList();
    }

    //  Trier par montant
    public List<Transaction> sortTransactionsByAmount() {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getAmount))
                .toList();
    }

    // Volume total échangé par actif
    public Map<String, Integer> getVolumeByAsset() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getAsset().getCode(),
                        Collectors.summingInt(Transaction::getQuantity)
                ));
    }

    //  Montant total BUY
    public double getTotalBuyAmount() {
        return transactions.stream()
                .filter(t -> t.getType().equals("BUY"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    //  Montant total SELL
    public double getTotalSellAmount() {
        return transactions.stream()
                .filter(t -> t.getType().equals("SELL"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // ===========================
    // Analyse performance par trader
    // ===========================

    // 10. Volume total par trader
    public Map<Integer, Double> getVolumeByTrader() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getTraderId,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    //  Nombre total d’ordres par trader
    public Map<Integer, Long> getOrdersCountByTrader() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getTraderId,
                        Collectors.counting()
                ));
    }

    //  Classement des traders par volume (top N)
    public List<Map.Entry<Integer, Double>> getTopTraders(int n) {
        return getVolumeByTrader().entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(n)
                .toList();
    }

    //  Volume total par instrument
    public Map<String, Integer> getGlobalVolumeByInstrument() {
        return getVolumeByAsset();
    }

    // Instrument le plus échangé
    public Optional<Map.Entry<String, Integer>> getMostTradedInstrument() {
        return getVolumeByAsset().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    //  Montant total BUY/SELL séparément
    public Map<String, Double> getBuySellTotals() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}


