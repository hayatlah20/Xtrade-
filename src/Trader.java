public class Trader extends Person {
    private double balance;
    private Portfolio<Asset> portfolio;

    public Trader(int id, String name, double balance) {
        super(id, name);
        if (balance <= 0)
            throw new IllegalArgumentException("Solde initial invalide");
        this.balance = balance;
        this.portfolio = new Portfolio<>();
    }

    public double getBalance() {
        return balance;
    }
    public Portfolio<Asset> getPortfolio() {
        return portfolio;
    }

    public void debit(double amount) {
        if (amount <= 0 || amount > balance)
            throw new IllegalArgumentException("Montant invalide");
        balance -= amount;
    }

    public void credit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Montant invalide");
        balance += amount;
    }
}

