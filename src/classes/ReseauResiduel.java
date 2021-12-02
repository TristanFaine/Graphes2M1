package src.classes;

import java.util.HashMap;

// Prend un reseau de transport dans le constructeur et retourne son reseau residuel
public class ReseauResiduel extends Reseau {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;

  private Noeud source;
  private Noeud puits;

  public ReseauResiduel(ReseauTransport reseau) throws CloneNotSupportedException {
    super();

    this.noeuds = new HashMap<String, Noeud>();
    this.arcs = new HashMap<String, Arc>();

    for (Noeud noeud : reseau.getNoeuds()) {
      Noeud noeudResiduel = new Noeud(noeud.getId(), noeud.getA(), noeud.getB());
      this.ajouterNoeud(noeudResiduel);
    }

    for (Arc arc : reseau.getArcs()) {

      // S'il est toujours possible de passer du flot sur cet arc alors on
      // crée l'arc avec la capacite restante dans le reseau residuel
      if (arc.getFlot() < arc.getCapacite()) {
        Noeud parent = this.getNoeudParId(arc.getParent().getId());
        Noeud enfant = this.getNoeudParId(arc.getEnfant().getId());
        Arc arcResiduelAller = new Arc(parent, enfant, arc.getCapacite());
        // arcResiduelAller.setFlot(arc.getFlot()); si nécessaire

        this.ajouterArc(arcResiduelAller);
      }

      // Si du flot passe déjà sur cet arc alors on crée l'arc retour
      // avec une capacité égale au flot passant actuellement
      // dans le reseau residuel
      if (arc.getFlot() > 0) {
        Noeud parent = this.getNoeudParId(arc.getParent().getId());
        Noeud enfant = this.getNoeudParId(arc.getEnfant().getId());

        // Inversion normale car on veut faire un arc retour
        Arc arcResiduelRetour = new Arc(enfant, parent, arc.getCapacite());
        // arcResiduelRetour.setFlot(arc.getFlot()); si nécessaire

        this.ajouterArc(arcResiduelRetour);
      }
      // this.arcs.put(arc.getId(), arc);
    }

    new Source(this);
    new Puits(this);
  }
}
