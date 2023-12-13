package up.mi.yjcc;

/**
 * Classe menu
 */
public class Menu {
    /**
     * Affiche le menu pour ajouter une route
     */
    public static void menuAjouterRoute() {
        System.out.println("***Menu d'une route***");
        System.out.println("1) ajouter une route;");
        System.out.println("2) fin");
        System.out.print("Choisissez une option : ");
    }

    /**
     * Affiche le menu pour ajouter une zone de recharge
     */
    public static void menuAjouterZoneRecharge() {
        System.out.println("***Menu d'une zone de recharge***");
        System.out.println("1) ajouter une zone de recharge;");
        System.out.println("2) retirer une zone de recharge;");
        System.out.println("3) fin;");
        System.out.print("Choisissez une option : ");
    }

    /**
     * Affiche le menu pour resoudre
     */
    public static void menuResoudre() {
        System.out.println("***Menu pour resoudre***");
        System.out.println("1) resoudre manuellement;");
        System.out.println("2) resoudre automatiquement;");
        System.out.println("3) sauvegarder;");
        System.out.println("4) fin.");
        System.out.print("Choisissez une option : ");
    }
}
