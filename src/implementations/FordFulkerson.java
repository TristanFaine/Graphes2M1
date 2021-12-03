package src.implementations;

import java.util.ArrayList;
import java.util.List;
import src.classes.Arc;
import src.classes.Noeud;
import src.classes.Reseau;
import src.classes.ReseauResiduel;
import src.classes.ReseauTransport;

public class FordFulkerson {
  private List<String> visites;

  // Return le reseau avec son flot maximal
  // Algorithme de Ford Fulkerson
  public Reseau calculFlotMax(ReseauTransport reseau) throws CloneNotSupportedException {
    // Commencer à partir de la source:
    // Tant qu'il existe un arc sortant avec flot < capacité:
    // Passer la sauce (ajouter val flot)

    // Ou plutôt :
    // Si existance un chemin de croissance (avoir réseau résiduel?)
    // Ajouter val flot le plus petit sur chaque arc (faire -val si arc retour)

    ReseauResiduel reseauResiduel = new ReseauResiduel(reseau);
    List<String> chemin = this.trouverCheminCroissance(reseauResiduel);

    System.out.println("Chemin augmentant : " + chemin);

    // while () {
    // Trouver le flot le plus petit
    // int flot = new ReseauResiduel(reseau).getFlotLePlusPetit();
    // Ajouter le flot sur chaque arc
    // reseau.ajouterFlot(flot);
    // }

    return null;
  }

  public List<String> trouverCheminCroissance(Reseau reseau) {
    this.visites = new ArrayList<>();
    List<String> chemin = this.parcoursProfondeur(reseau, reseau.getSource(), null);

    return chemin;
  }

  public List<String> parcoursProfondeur(Reseau reseau, Noeud noeud, List<String> chemin) {

    if (chemin == null) {
      chemin = new ArrayList<>();
    }

    this.visites.add(noeud.getId());
    chemin.add(noeud.getId());

    for (Arc arcSortant : noeud.getArcsSortants()) {
      Noeud voisin = arcSortant.getEnfant();

      if (voisin.getId() == "puits") {
        chemin.add("puits");
        return chemin;
      }

      if (!this.visites.contains(voisin.getId())) {
        return this.parcoursProfondeur(reseau, voisin, chemin);
      }
    }

    return null;
  }
}
