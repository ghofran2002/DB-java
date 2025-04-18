package prat;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VolDAO dao = new VolDAO();
        int choix;

        do {
            System.out.println("\n===== MENU AÉROPORT =====");
            System.out.println("1. Afficher tous les vols");
            System.out.println("2. Ajouter un nouveau vol");
            System.out.println("3. Modifier la destination d’un vol");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    List<Vol> vols = dao.getAllVols();
                    if (vols.isEmpty()) {
                        System.out.println(" Aucun vol enregistré.");
                    } else {
                        vols.forEach(System.out::println);
                    }
                    break;

                case 2:
                    System.out.print("Numéro du vol : ");
                    String num = scanner.nextLine();
                    System.out.print("Ville de départ : ");
                    String depart = scanner.nextLine();
                    System.out.print("Ville d’arrivée : ");
                    String arrivee = scanner.nextLine();

                    Vol vol = new Vol(num, depart, arrivee);
                    dao.ajouterVol(vol);
                    break;

                case 3:
                    System.out.print("Numéro du vol à modifier : ");
                    String numero = scanner.nextLine();

                    Vol v = dao.chercherVol(numero);
                    if (v != null) {
                        System.out.print("Nouvelle ville d’arrivée : ");
                        String newDest = scanner.nextLine();
                        dao.modifierDestination(numero, newDest);
                    } else {
                        System.out.println("Vol introuvable.");
                    }
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println(" Choix invalide.");
            }

        } while (choix != 4);

        scanner.close();
    }
}
