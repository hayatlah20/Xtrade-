import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TradingPlatform market = new TradingPlatform();

        boolean run = true;

        while (run) {
            System.out.println("\n--- XTrade Menu ---");
            System.out.println("1. Ajouter actif");
            System.out.println("2. Afficher actifs");
            System.out.println("3. Ajouter trader");
            System.out.println("4. Consulter portefeuille");
            System.out.println("5. Acheter actif");
            System.out.println("6. Vendre actif");
            System.out.println("7. Historique");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int c = sc.nextInt();
            sc.nextLine();

            try {
                switch (c) {
                    case 1:
                        System.out.print("Type (1=Stock, 2=Crypto): ");
                        int t = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Code: ");
                        String code = sc.nextLine();

                        System.out.print("Nom: ");
                        String name = sc.nextLine();

                        System.out.print("Prix: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        if (t == 1) {
                            System.out.print("Marché: ");
                            String m = sc.nextLine();
                            market.addAsset(new Stock(code, name, price, m));
                        } else {
                            System.out.print("Blockchain: ");
                            String b = sc.nextLine();
                            market.addAsset(new CryptoCurrency(code, name, price, b));
                        }
                        break;

                    case 2:
                        market.showAssets();
                        break;

                    case 3:
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nom: ");
                        String n = sc.nextLine();

                        System.out.print("Solde: ");
                        double s = sc.nextDouble();

                        market.addTrader(new Trader(id, n, s));
                        break;

                    case 4:
                        System.out.print("ID Trader: ");
                        int tid = sc.nextInt();

                        Trader tr = market.getTraderById(tid);
                        System.out.println("Solde: " + tr.getBalance());
                        tr.getPortfolio().getAssets().forEach(
                                (a, q) -> System.out.println(a.getName() + " | " + q)
                        );
                        System.out.println("Valeur totale: " +
                                tr.getPortfolio().getTotalValue());
                        break;

                    case 5:
                        System.out.print("ID Trader: ");
                        int tb = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Code actif: ");
                        String ab = sc.nextLine();

                        System.out.print("Quantité: ");
                        int qb = sc.nextInt();

                        market.buyAsset(tb, ab, qb);
                        break;

                    case 6:
                        System.out.print("ID Trader: ");
                        int ts = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Code actif: ");
                        String as = sc.nextLine();

                        System.out.print("Quantité: ");
                        int qs = sc.nextInt();

                        market.sellAsset(ts, as, qs);
                        break;

                    case 7:
                        market.showTransactions();
                        break;

                    case 0:
                        run = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
        sc.close();
    }
}