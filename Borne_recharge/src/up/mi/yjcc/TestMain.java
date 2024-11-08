package up.mi.yjcc;

import java.util.Scanner;

/**
 * Classe TestMain où on execute le programme
 */
public class TestMain {
    public static void main(String[] args) {
        // Initialisation des variables
        Scanner sc = new Scanner(System.in);
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        // Prend les arguments passés dans la ligne de commande
        String filePath = args.length > 0 ? args[0] : null;

        if (filePath != null) {
            try {
                communaute.chargerDepuisFichier(filePath, communaute, sc);
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
            }
        } else {
            System.out.println("Vous n'avez pas precisé un chemin de fichier.");
            System.out.println("Configuration vide appliqué.");
            communaute.configurationSansFichierCharge(sc, communaute);
        }
    }

}
