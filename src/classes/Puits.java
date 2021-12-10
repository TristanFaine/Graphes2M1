package src.classes;

public class Puits extends Noeud {
  public Puits(Reseau reseau) {
    super("puits", 0, 0);

    reseau.setPuits(this);
    this.setPuits(true);

    // Ajouter au réseau un noeud "source", de coordonnées non précisé, son id est "source"
    reseau.ajouterNoeud(this);

    // Pour chaque noeud du réseau, ajouter un arc de capacité noeud.getA()
    /*
     * for (Noeud noeud : reseau.getNoeuds()) { if (!noeud.getId().equals("source") &&
     * !noeud.getId().equals("puits")) { Arc arc = new Arc(noeud, this, noeud.getB());
     * reseau.ajouterArc(arc); } }
     */

  }
}
