package src.classes;

public class Source extends Noeud {
  public Source(Reseau reseau) {
    super("source", 0, 0);

    reseau.setSource(this);
    this.setSource(true);

    // Ajouter au réseau un noeud "source", de coordonnées non précisé, son id est "source"
    reseau.ajouterNoeud(this);

    // Pour chaque noeud du réseau, ajouter un arc de capacité noeud.getA()
    /*
     * for (Noeud noeud : reseau.getNoeuds()) { if (!noeud.getId().equals("source") &&
     * !noeud.getId().equals("puits")) { Arc arc = new Arc(this, noeud, noeud.getA());
     * reseau.ajouterArc(arc); } }
     */

  }
}
