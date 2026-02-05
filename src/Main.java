import java.time.LocalDateTime;
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
                        boolean subRun = true;
                        while (subRun) {
                            System.out.println("\n--- Historique & Analyse ---");
                            System.out.println("1. Afficher toutes les transactions");
                            System.out.println("2. Transactions d’un trader");
                            System.out.println("3. Filtrer par type (BUY/SELL)");
                            System.out.println("4. Filtrer par actif");
                            System.out.println("5. Filtrer par intervalle de dates");
                            System.out.println("6. Trier par date");
                            System.out.println("7. Trier par montant");
                            System.out.println("8. Volume total par actif");
                            System.out.println("9. Montant total BUY/SELL");
                            System.out.println("10. Top traders (par volume)");
                            System.out.println("11. Instrument le plus échangé");
                            System.out.println("0. Retour");

                            System.out.print("Choix : ");
                            int sub = sc.nextInt();
                            sc.nextLine();

                            switch (sub) {
                                case 1:
                                    market.showTransactions();
                                    break;

                                case 2:
                                    System.out.print("ID Trader: ");
                                    int tid2 = sc.nextInt();
                                    sc.nextLine();
                                    market.getTransactionsByTrader(tid2)
                                            .forEach(System.out::println);
                                    break;

                                case 3:
                                    System.out.print("Type (BUY/SELL): ");
                                    String type = sc.nextLine();
                                    market.getTransactionsByType(type)
                                            .forEach(System.out::println);
                                    break;

                                case 4:
                                    System.out.print("Code actif: ");
                                    String code2 = sc.nextLine();
                                    market.getTransactionsByAsset(code2)
                                            .forEach(System.out::println);
                                    break;

                                case 5:
                                    System.out.print("Date début (YYYY-MM-DDTHH:MM): ");
                                    LocalDateTime start = LocalDateTime.parse(sc.nextLine());

                                    System.out.print("Date fin (YYYY-MM-DDTHH:MM): ");
                                    LocalDateTime end = LocalDateTime.parse(sc.nextLine());

                                    market.getTransactionsByDate(start, end)
                                            .forEach(System.out::println);
                                    break;

                                case 6:
                                    market.sortTransactionsByDate()
                                            .forEach(System.out::println);
                                    break;

                                case 7:
                                    market.sortTransactionsByAmount()
                                            .forEach(System.out::println);
                                    break;

                                case 8:
                                    market.getVolumeByAsset()
                                            .forEach((k, v) -> System.out.println(k + " | Volume: " + v));
                                    break;

                                case 9:
                                    System.out.println("Total BUY: " + market.getTotalBuyAmount());
                                    System.out.println("Total SELL: " + market.getTotalSellAmount());
                                    break;

                                case 10:
                                    System.out.print("Top N traders: ");
                                    int ne = sc.nextInt();
                                    sc.nextLine();
                                    market.getTopTraders(ne)
                                            .forEach(e -> System.out.println("Trader " + e.getKey() + " | Volume: " + e.getValue()));
                                    break;

                                case 11:
                                    market.getMostTradedInstrument()
                                            .ifPresentOrElse(
                                                    e -> System.out.println("Instrument le plus échangé: " + e.getKey() + " | Volume: " + e.getValue()),
                                                    () -> System.out.println("Aucune transaction trouvée")
                                            );
                                    break;

                                case 0:
                                    subRun = false;
                                    break;
                            }
                        }
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


