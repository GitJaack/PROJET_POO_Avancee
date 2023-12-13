package up.mi.yjcc;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe d'une ville
 */
public class Ville {
    private String nom; // Nom de la ville
    private boolean recharge; // Indique si la ville est équipé d'une zone de recharge(true = oui, false=non)
    private List<Ville> voisins; // Liste des voisins

    /**
     * Instance d'une ville
     */
    public Ville(String nom) {
        this.nom = nom;
        recharge = false;
        this.voisins = new ArrayList<Ville>();
    }

    /**
     * Recupere le nom
     * 
     * @return Nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom
     * 
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Verifie si il y a une recharge
     * 
     * @return true s'il y a une recharge ; false sinon
     */
    public boolean isRecharge() {
        return recharge;
    }

    /**
     * Modifie à true s'il y a zone de recharge ; false sinon
     * 
     * @param recharge
     */
    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }

    /**
     * Recupere la liste des voisins
     * 
     * @return La liste des voisins de la ville
     */
    public List<Ville> getVoisins() {
        return voisins;
    }

    /**
     * Ajoute un voisin
     * 
     * @param voisin
     */
    public void ajouterVoisin(Ville voisin) {
        voisins.add(voisin);
    }

    /**
     * Renvoie un objet Ville en format de chaine de caractere
     */
    @Override
    public String toString() {
        return nom;
    }

}
