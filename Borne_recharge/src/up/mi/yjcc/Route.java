package up.mi.yjcc;

/**
 * Classe d'une route
 */
public class Route {
    private Ville villeA;
    private Ville villeB;

    /**
     * Instance d'une route
     */
    public Route(Ville villeA, Ville villeB) {
        this.villeA = villeA;
        this.villeB = villeB;
    }

    /**
     * Recupere la villeA
     * 
     * @return villeA
     */
    public Ville getVilleA() {
        return villeA;
    }

    /**
     * Modifie la villeA
     * 
     * @param villeA
     */
    public void setVilleA(Ville villeA) {
        this.villeA = villeA;
    }

    /**
     * Recupere la villeB
     * 
     * @return villeB
     */
    public Ville getVilleB() {
        return villeB;
    }

    /**
     * Modifie la villeB
     * 
     * @param villeB
     */
    public void setVilleB(Ville villeB) {
        this.villeB = villeB;
    }

    /**
     * Renvoie l'objet Route en chaine de caratere
     */
    @Override
    public String toString() {
        return villeA + " <-> " + villeB;
    }

}
