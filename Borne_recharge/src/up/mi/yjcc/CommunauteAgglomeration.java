package up.mi.yjcc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Classe d'une communaute d'agglomeration
 */
public class CommunauteAgglomeration {
    private List<Ville> villes;// Liste des villes

    /**
     * Instance une communaute d'agglomeration tout en initialisant une liste de
     * ville
     */
    public CommunauteAgglomeration() {
        this.villes = new ArrayList<Ville>();
    }

    /**
     * Recupere la liste de ville
     * 
     * @return Liste des villes
     */
    public List<Ville> getListeVilles() {
        return villes;
    }

    /**
     * Trouve une ville grace a son nom
     * 
     * @param nomVille Nom de la ville a trouver
     * @return La ville qu'on cherche ou null si elle n'existe pas
     */
    private Ville trouverVille(String nomVille) {
        for (Ville ville : villes) {
            // Verifie si le nom de la ville correspond à l'element ville
            if (ville.getNom().equals(nomVille)) {
                return ville;
            }
        }
        return null;
    }

    /**
     * Creer une agglomeration en fonction de nbVille
     * 
     * @param sc      Nom des villes entrees par l'utilisateur
     * @param nbVille Nombre de ville souhaitees dans la communaute
     */
    public void creerAgglomeration(Scanner sc, int nbVille) {
        String nom;
        for (int i = 0; i < nbVille; i++) {
            do {
                System.out.print("Nom de la ville n°" + (i + 1) + ": ");
                nom = sc.nextLine();
                if (trouverVille(nom) != null) {
                    System.out.println("Le nom de la ville est deja utilisé.");
                }
            } while (trouverVille(nom) != null);
            villes.add(new Ville(nom)); // ajoute la ville a la liste
        }
    }

    /**
     * Cree une route entre 2 villes
     * 
     * @param sc Ville entree par l'utilisateur
     */
    public void creerRoute(Scanner sc) {
        System.out.println("Entre quelles villes faut-il ajouter une route ?");
        System.out.print("Entrez le nom de la ville : ");
        String nomVille1 = sc.nextLine();
        System.out.print("Entrez le nom de la seconde ville : ");
        String nomVille2 = sc.nextLine();

        // Initialisation des villes
        Ville villeA = null;
        Ville villeB = null;

        // Recherche des villes dans la liste
        for (Ville ville : villes) {
            if (ville.getNom().equalsIgnoreCase(nomVille1)) {
                villeA = ville;
            }
            if (ville.getNom().equalsIgnoreCase(nomVille2)) {
                villeB = ville;
            }
        }
        // Verifie si les villes existent
        if (villeA != null && villeB != null) {
            // Verifie si la route existe dejà
            boolean routeExiste = villeA.getVoisins().contains(villeB);
            // Ajout de la route ou affichage d'un message si elle existe dejà
            if (routeExiste) {
                System.out.println("La route entre ces deux villes existe dejà.");
                System.out.println();
            } else if (nomVille1.equals(nomVille2)) {
                System.out.println("Vous ne pouvez pas choisir 2 fois la même ville. Veuillez reessayer");
                System.out.println();
            } else {
                Route nouvelleRoute = new Route(villeA, villeB); // Creation de la route entre les deux villes
                villeA.ajouterVoisin(villeB);// On ajoute la ville B dans la liste des voisins de la ville A
                villeB.ajouterVoisin(villeA);// On ajoute la ville A dans la liste des voisins de la ville B
                System.out.println("Route ajoutée : " + nouvelleRoute);
                System.out.println();
            }
        } else {
            System.out.println("Ville(s) non trouvee(s). Veuillez reessayer.");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Affichage des routes de la communaute
     * 
     */
    private void afficherRoutesCommunaute() {
        System.out.println("Routes de la communaute d'agglomeration :");

        // Utiliser un ensemble pour suivre les routes déjà affichées
        Set<String> routesAffichees = new HashSet<>();

        for (Ville ville : villes) {
            for (Ville voisin : ville.getVoisins()) {
                // Concaténer les noms de villes dans l'ordre alphabétique
                String route = (ville.getNom().compareTo(voisin.getNom()) < 0)
                        ? ville.getNom() + " <-> " + voisin.getNom()
                        : voisin.getNom() + " <-> " + ville.getNom();

                // Vérifie si la route a déjà été ajouter dans l'ensemble
                if (!routesAffichees.contains(route)) {
                    System.out.println(route);
                    routesAffichees.add(route); // Ajouter la route à l'ensemble
                }
            }
        }
        System.out.println();
    }

    /**
     * Ajoute une zone de recharge dans la ville
     * 
     * @param ville Ville où on ajoute la zone de recharge
     */
    public void ajouterRecharge(Ville ville) {
        if (!ville.isRecharge()) {
            ville.setRecharge(true); // On ajoute la zone de recharge
            System.out.println("Zone de recharge ajoutée à la ville " + ville.getNom());
        } else {
            System.out.println();
            System.out.println("Cette ville est déjà équipé d'une zone de recharge.");
        }
    }

    /**
     * Demande a ajouter une zone de recharge dans la ville
     * 
     * @param sc Ville entree par l'utilisateur
     */
    public void creerZoneRecharge(Scanner sc) {
        System.out.print("Dans quelle ville souhaitez-vous ajouter la zone de recharge ? ");
        String villeRecharge = sc.nextLine();
        boolean villeTrouvee = false;
        // Cherche la ville correspondante
        for (Ville ville : villes) {
            if (ville.getNom().equalsIgnoreCase(villeRecharge)) {
                ajouterRecharge(ville);
                villeTrouvee = true;
                break;// on sort de la boucle car on a trouvé la ville
            }
        }
        if (!villeTrouvee) {
            System.out.println("Ville non trouvée. Veuillez réessayer.");
        }
        System.out.println();
    }

    /**
     * Recupere les voisins sans recharge d'une ville
     * 
     * @param ville Ville où on souhaite enlever la zone de recharge
     * @return La liste des voisins sans recharge
     */
    public List<Ville> getVoisinsSansRecharge(Ville ville) {
        List<Ville> voisinsSansRecharge = new ArrayList<>();

        ville.setRecharge(false);// On l'enleve pour ne pas le compter dans les voisins car il sera enlevé

        // On cherche les voisins de ville
        for (Ville voisin : ville.getVoisins()) {
            if (!voisin.isRecharge()) {
                voisinsSansRecharge.add(voisin);
                // On vérifie si les voisins des voisins de ville ont une zone de recharge
                for (Ville voisinDeVoisin : voisin.getVoisins()) {
                    if (voisinDeVoisin.isRecharge()) {
                        voisinsSansRecharge.remove(voisin);
                    }
                }

            }
        }
        ville.setRecharge(true);
        return voisinsSansRecharge;
    }

    /**
     * Verifie si la ville respecte la contrainte d'accessibilite
     * 
     * @param ville Ville où on souhaite enlever la zone de recharge
     * @return true si on peut retirer la zone de recharge, false sinon
     */
    public boolean contrainteAccessibilite(Ville ville) {
        boolean peutRetirer = false;
        // On recupere la liste des voisins sans recharge de la ville
        List<Ville> voisinsSansRecharge = getVoisinsSansRecharge(ville);
        for (Ville voisin : ville.getVoisins()) {
            if (voisin.isRecharge()) {
                peutRetirer = true;
                break;
            }
        }

        if (!peutRetirer) {
            voisinsSansRecharge.add(ville);
        }

        // Si la liste des voisins sans recharge n'est pas vide
        if (!voisinsSansRecharge.isEmpty()) {
            peutRetirer = false;
        }

        if (peutRetirer) {
            ville.setRecharge(false);
            System.out.println("Zone de recharge retirée de la ville : " + ville.getNom());
            System.out.println();

        } else {
            System.out.println();
            System.out.println(
                    "Le retrait est impossible. Les villes suivantes se retrouveraient sans zone de recharge dans leur voisinage :");
            for (Ville voisinSansRecharge : voisinsSansRecharge) {
                System.out.println("- " + voisinSansRecharge.getNom());
            }
        }
        return peutRetirer;
    }

    /**
     * Retire une zone de recharge dans la ville si elle respecte la contrainte
     * 
     * @param sc Ville entree par l'utilisateur
     */
    public void retirerRecharge(Scanner sc) {
        System.out.print("Dans quelle ville souhaitez-vous retirer la zone de recharge ? ");
        String villeRetirerRecharge = sc.nextLine();
        boolean villeTrouvee = false;

        for (Ville ville : villes) {
            if (ville.getNom().equalsIgnoreCase(villeRetirerRecharge)) {
                villeTrouvee = true;
                if (ville.isRecharge()) {
                    if (contrainteAccessibilite(ville)) {// On verifie la contrainte pour la ville et les voisins
                        ville.setRecharge(false);
                    }
                } else {
                    System.out.println();
                    System.out.println("Cette ville n'est pas équipée de zone de recharge");
                }
            }
        }
        if (!villeTrouvee) {
            System.out.println("Ville non trouvée. Veuillez réessayer.");
        }
    }

    /**
     * Affiche la liste des villes
     */
    public void afficherListeVilles() {
        System.out.println("Liste des villes :");
        for (Ville ville : villes) {
            System.out.println("- " + ville.getNom());
        }
        System.out.println();
    }

    /**
     * Affiche la liste des villes qui ont une zone de recharge
     */
    public void afficherListeVilleRecharge() {
        System.out.println("Liste des villes avec recharge :");
        for (Ville ville : villes) {
            if (ville.isRecharge()) {
                System.out.println("- " + ville.getNom());
            }
        }
        System.out.println();
    }

    /**
     * Menu pour configurer les routes
     * 
     * @param sc Information rentree par l'utilisateur
     */
    public void configurationRoute(Scanner sc) {
        int choix = 0;
        do {
            try {
                Menu.menuAjouterRoute();
                choix = sc.nextInt();
                sc.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

                switch (choix) {
                    case 1:
                        System.out.println();
                        creerRoute(sc);
                        afficherRoutesCommunaute();
                        afficherListeVilles();
                        break;
                    case 2:
                        System.out.println();
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                        System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                System.out.println();
                sc.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie incorrecte
            }
        } while (choix != 2);
    }

    /**
     * Configure une communaute d'agglomeration sans fichier
     * 
     * @param sc Information entree par l'utilisateur
     * @param CA Communaute d'agglomeration
     */
    public void configurationSansFichierCharge(Scanner sc, CommunauteAgglomeration CA) {
        int n = 0;

        do {
            try {
                System.out.print("Combien de villes voulez-vous créer ? ");
                n = sc.nextInt();
                sc.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
                if (n < 0) {
                    System.out.println("Veuillez entrer un nombre > 0");
                    System.out.println();
                }
            } catch (InputMismatchException e) {
                System.err.println("Veuillez entrer un nombre > 0");
                System.out.println();
                sc.nextLine();
            }
        } while (n < 1);

        creerAgglomeration(sc, n);

        System.out.println();

        configurationRoute(sc);

        System.out.println();

        configurationResoudre(sc, CA);
    }

    /**
     * Sauvergarde les donnees dans un fichier
     * 
     * @param sc Nom du fichier et le chemin vers ce fichier entree par
     *           l'utilisateur
     * @throws IOException
     */
    public void writeToFile(Scanner sc) throws IOException {
        try {

            System.out.print("Où voulez-vous sauvegarder le fichier (C:\\...\\nomDuFichier.ca) ? ");
            String pathName = sc.nextLine();
            Path path = Paths.get(pathName); // Crée un objet Path à partir du chemin spécifié

            // Vérifie si le chemin se termine par un séparateur de répertoire
            if (pathName.endsWith("\\")) {
                System.out.println("Veuillez fournir un nom de fichier après le chemin.");
                return;
            }

            // Vérifie si le fichier existe déjà
            if (Files.exists(path)) {
                System.out.println();
                System.out.println("Fichier existe déjà.");
                return;
            }
            // Crée le fichier
            Files.createFile(path);

            System.out.println();
            System.out.println("Fichier créé dans le chemin : " + path);

            // Initialise un FileWriter pour écrire dans le fichier
            FileWriter fw = new FileWriter(pathName);

            // Initialise un BufferedWriter pour écrire dans le fichier de manière optimisée
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                sauvegardeDonneeDansFichier(bw);
            }

            System.out.println();
            System.out.println("Données sauvegardées avec succès dans le chemin : " + pathName);
            System.out.println();

        } catch (IOException e) { // Gère les erreurs d'entrée/sortie et affiche un message d'erreur
            System.err.println("Erreur lors de la sauvegarde dans le chemin : " + e.getMessage());
        }
    }

    /**
     * Ecrit les donnees dans le fichier
     * 
     * @param bw Permet d'ecrire dans le fichier
     * @throws IOException
     */
    public void sauvegardeDonneeDansFichier(BufferedWriter bw) throws IOException {
        // Enregistre chaque ville dans le fichier avec le format
        // "ville(NOM_DE_LA_VILLE)."
        for (Ville ville : villes) {
            bw.write("ville(" + ville.getNom() + ").\n");
        }

        // Utilisation d'un ensemble pour supprimer les doublons
        Set<String> routesAffichees = new HashSet<>();

        // Enregistre chaque route entre villes dans le fichier avec le format
        // "route(VILLE1, VILLE2)."
        for (Ville ville : villes) {
            for (Ville voisin : ville.getVoisins()) {
                // Construit une représentation de la route (ordonnée alphabétiquement) entre
                // deux villes
                String route = (ville.getNom().compareTo(voisin.getNom()) < 0)
                        ? ville.getNom() + " <-> " + voisin.getNom()
                        : voisin.getNom() + " <-> " + ville.getNom();

                // Vérifie si l'ensemble contient le String route
                if (!routesAffichees.contains(route)) {
                    bw.write("route(" + ville.getNom() + "," + voisin.getNom() + ").\n");
                    routesAffichees.add(route); // Ajoute la route à l'ensemble
                }
            }
        }
        // Enregistre chaque ville de recharge dans le fichier avec le format
        // "recharge(NOM_DE_LA_VILLE)."
        for (Ville ville : villes) {
            if (ville.isRecharge()) {
                bw.write("recharge(" + ville.getNom() + ").\n");
            }
        }
    }

    /**
     * Charge un fichier qui configure une communaute d'agglomeration
     * 
     * @param nomFichier Nom du fichier a charger
     * @param CA         Communaute d'agglomeration
     */
    public void chargerDepuisFichier(String nomFichier, CommunauteAgglomeration CA, Scanner sc) {
        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            while (scanner.hasNextLine()) { // tant qu'il y a une autre ligne dans le fichier
                String ligne = scanner.nextLine().trim();
                // Verifie si la ligne n'est pas vide et si le fichier contient des informations
                // sur les recharges
                if (!ligne.isEmpty() && fichierContientRecharges(nomFichier)) {
                    traiterLigneVille(ligne);
                    traiterLigneRoute(ligne);
                    traiterLigneRecharge(ligne, CA);
                } else {
                    traiterLigneVille(ligne);
                    traiterLigneRoute(ligne);
                }
            }
            // Affiche un message indiquant que les donnees ont ete chargées avec succès
            System.out.println();
            System.out.println("Données chargées avec succès depuis le fichier : " + nomFichier);
            System.out.println();
            // configurationResoudreAvecPath(nomFichier, sc, CA);
            configurationResoudre(sc, CA);

        } catch (FileNotFoundException e) {// Affiche un message d'erreur si le fichier spécifié n'a pas été trouvé
            System.out.println();
            System.err.println("Le fichier spécifié n'a pas été trouvé : " + e.getMessage());
            System.out.println();
            System.out.println("Configuration vide appliqué.");
            System.out.println();
            configurationSansFichierCharge(sc, CA); // Le fichier n'a pas été trouvé
        }
    }

    /**
     * Configure les villes depuis le fichier
     * 
     * @param ligne, Ligne du fichier
     */
    public void traiterLigneVille(String ligne) {
        // Verifie si la ligne commence par "ville"
        if (ligne.startsWith("ville")) {
            // Extraction du nom de la ville
            String nomVille = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));
            // Ajout de la ville à la communauté
            villes.add(new Ville(nomVille));
        }
    }

    /**
     * Configure les routes depuis le fichier
     * 
     * @param ligne, Ligne du fichier
     */
    public void traiterLigneRoute(String ligne) {
        // Verifie si la ligne commence par "route"
        if (ligne.startsWith("route")) {
            // Extraction des noms des villes liées par la route separé par une virgule
            String[] parties = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")")).split(",");
            String nomVilleA = parties[0].trim();
            String nomVilleB = parties[1].trim();
            // Recherche des objets Ville correspondants
            Ville villeA = trouverVille(nomVilleA);
            Ville villeB = trouverVille(nomVilleB);
            // Ajout de la route entre les villes
            if (villeA != null && villeB != null) {
                villeA.ajouterVoisin(villeB);
                villeB.ajouterVoisin(villeA);
            } else {
                System.out.println("Certaine route entre deux villes ont mal été écrite dans le fichier.");
            }
        }
    }

    /**
     * Configure les zones de recharge depuis le fichier
     * 
     * @param ligne, Ligne du fichier
     */
    public void traiterLigneRecharge(String ligne, CommunauteAgglomeration CA) {
        // Verifie si la ligne commence par "recharge"
        if (ligne.startsWith("recharge")) {
            String nomVilleRecharge = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));
            // Ajout d'une recharge dans la ville
            Ville ville = trouverVille(nomVilleRecharge);
            if (ville != null) {
                ajouterRecharge(ville);
            } else {
                System.out.println("Erreur : La ville " + nomVilleRecharge + " n'a pas été trouvée.");
            }

        }

    }

    /**
     * Verifie si il y a au moins une zone de recharge representee dans le fichier
     * 
     * @param nomFichier Nom du fichier a verifier
     * @return true si il y a au moins une zone de recharge, false sinon
     */
    public boolean fichierContientRecharges(String nomFichier) {
        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (ligne.startsWith("recharge")) {
                    return true; // On a trouvé au moins une zone de recharge
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier spécifié n'a pas été trouvé : " + e.getMessage());
        }
        return false; // Aucune zone de recharge trouvée
    }

    /**
     * Verifie si une communaute d'agglomeration respecte la contrainte
     * d'accessiblite
     * 
     * @return true si la communaute d'agglomeration respecte la contrainte; false
     *         sinon
     */
    public boolean communauteRespecteContrainte() {
        // Parcours les villes
        for (Ville ville : villes) {
            // Verifie si la ville n'a pas de recharge
            if (!ville.isRecharge()) {
                boolean aVoisinAvecRecharge = false;

                // Parcours les voisins de la ville
                for (Ville voisin : ville.getVoisins()) {
                    // Verifie si voisin a une recharge
                    if (voisin.isRecharge()) {
                        aVoisinAvecRecharge = true;
                        break;
                    }
                }
                // Verifie si la communaute respecte pas la contrainte
                if (!aVoisinAvecRecharge) {
                    for (Ville v : villes) {
                        v.setRecharge(true);
                    }
                    System.out.println();
                    System.out.println(
                            "La communaute ne respecte pas la contrainte. Chaques villes a été équipé d'une recharge.");
                    System.out.println();
                    return false;
                }

            }
        }
        System.out.println();
        System.out.println("La communaute respecte la contrainte.");
        System.out.println();
        return true;
    }

    /**
     * Menu pour resoudre la contrainte manuellement
     * 
     * @param sc Information rentree par l'utilisateur
     */
    public void resoudreManuellement(Scanner sc) {
        int choix = 0;
        do {
            try {
                System.out.println();
                Menu.menuAjouterZoneRecharge();
                choix = sc.nextInt();
                sc.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

                switch (choix) {
                    case 1:
                        System.out.println();
                        afficherRoutesCommunaute();
                        afficherListeVilles();
                        afficherListeVilleRecharge();
                        creerZoneRecharge(sc);
                        afficherRoutesCommunaute();
                        afficherListeVilles();
                        afficherListeVilleRecharge();
                        communauteRespecteContrainte();
                        break;
                    case 2:
                        System.out.println();
                        afficherRoutesCommunaute();
                        afficherListeVilleRecharge();
                        retirerRecharge(sc);
                        communauteRespecteContrainte();
                        break;
                    case 3:
                        System.out.println();
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                        System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                System.out.println();
                sc.nextLine(); // Pour consommer la nouvelle lignrestante après la saisie incorrecte
            }

        } while (choix != 3);
    }

    /**
     * Menu pour configurer les zones de recharge ou sauvegarder les changements
     * 
     * @param sc Information rentree par l'utilisateur
     * @param CA Communaute d'agglomeration
     */
    public void configurationResoudre(Scanner sc, CommunauteAgglomeration CA) {
        int choix = 0;
        int k = 100;

        do {
            try {
                Menu.menuResoudre();
                choix = sc.nextInt();
                sc.nextLine();
                switch (choix) {
                    case 1:
                        communauteRespecteContrainte();
                        resoudreManuellement(sc);
                        communauteRespecteContrainte();
                        System.out.println();
                        break;
                    case 2:
                        resoudreAutomatiquement(CA, k);
                        System.out.println("*** Résultat après la résolution automatique ***");
                        communauteApresResoudreAutomatique(CA);
                        communauteRespecteContrainte();
                        System.out.println();
                        break;
                    case 3:
                        try {
                            writeToFile(sc);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println();
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                        System.out.println();
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                System.out.println();
                sc.nextLine();
            }

        } while (choix != 4);
    }

    /**
     * Donne le nombre de zone de recharge construite
     * 
     * @param CA Communaute d'agglomeration
     * @return Le nombre de zone de recharge construite
     */
    public int score(CommunauteAgglomeration CA) {
        int score = 0;// nombre de zone de recharge construite
        for (Ville ville : CA.getListeVilles()) {
            if (ville.isRecharge()) {
                score++; // Incremente score
            }
        }
        return score;
    }

    /**
     * Choisis une ville aleatoirement dans la communaute
     * 
     * @param CA Communaute d'agglomeration
     * @return La ville obtenue
     */
    public Ville villeAleatoire(CommunauteAgglomeration CA) {
        // Choisi au hasard une ville dans la communauté
        Random random = new Random();
        // Génère un index aléatoire en fonction de la taille de la liste des villes
        // dans la communauté
        int index = random.nextInt(CA.getListeVilles().size());
        // Retourne la ville correspondante à l'index généré
        return CA.getListeVilles().get(index);
    }

    /**
     * Resout automatiquement le problème d'optimisation en modifiant l'etat de
     * recharge des villes dans la communaute d'agglomeration pour ameliorer le
     * score.
     * 
     * @param CA communaute d'agglomeration
     * @param k  nombre d'iteration qu'on veut faire
     * @return la communaute d'agglomeration
     */
    public CommunauteAgglomeration resoudreAutomatiquement(CommunauteAgglomeration CA, int k) {
        int i = 0; // Initialise le compteur d'itérations à zéro
        int scoreCourant = score(CA); // Calcule le score initial de la communauté
        // Boucle de résolution automatique avec au plus k itérations
        while (i < k) {
            Ville v = villeAleatoire(CA);// Sélectionne aléatoirement une ville dans la communauté

            if (v.isRecharge()) {
                v.setRecharge(false);
            } else {
                v.setRecharge(true);
            }

            // Vérifie si la modification a amélioré le score
            if (score(CA) < scoreCourant) {
                i = 0; // Réinitialise le compteur si le score s'améliore
                scoreCourant = score(CA); // Met à jour le score courant
            } else {
                i++; // Incrémente le compteur d'itérations
            }
        }
        return CA;// Retourne la communauté d'agglomération après la résolution automatique
    }

    /**
     * Affiche la communaute d'agglomeration après la resolution automatique
     * 
     * @param CA communaute d'agglomeration
     */
    public void communauteApresResoudreAutomatique(CommunauteAgglomeration CA) {
        System.out.println();
        afficherRoutesCommunaute();
        afficherListeVilles();
        afficherListeVilleRecharge();
    }
}
