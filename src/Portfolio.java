import java.util.HashMap;
import java.util.Map;

public class Portfolio<T extends Asset> {
    private Map<T, Integer> assets = new HashMap<>();

    public void addAsset(T asset, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantité invalide");
        assets.put(asset, assets.getOrDefault(asset, 0) + quantity);
    }

    public void removeAsset(T asset, int quantity) {
        if (!assets.containsKey(asset))
            throw new IllegalArgumentException("Actif non détenu");

        int current = assets.get(asset);
        if (quantity <= 0 || quantity > current)
            throw new IllegalArgumentException("Quantité invalide");

        if (quantity == current)
            assets.remove(asset);
        else
            assets.put(asset, current - quantity);
    }

    public double getTotalValue() {
        double total = 0;
        for (Map.Entry<T, Integer> entry : assets.entrySet()) {
            total += entry.getKey().getUnitPrice() * entry.getValue();
        }
        return total;
    }

    public Map<T, Integer> getAssets() {
        return assets;
    }

}

