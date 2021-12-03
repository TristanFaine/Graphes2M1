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
  public int calculFlotMax(ReseauTransport reseau) throws CloneNotSupportedException {
    // Commencer à partir de la source:
    // Tant qu'il existe un arc sortant avec flot < capacité:
    // Passer la sauce (ajouter val flot)

    // Ou plutôt :
    // Si existance un chemin de croissance (avoir réseau résiduel?)
    // Ajouter val flot le plus petit sur chaque arc (faire -val si arc retour)

    int tours = 0;

    ReseauResiduel reseauResiduel = new ReseauResiduel(reseau);
    List<String> chemin = this.trouverCheminCroissance(reseauResiduel);

    while (chemin != null) {
      int capaciteMinimum = this.capaciteMinimumChemin(reseauResiduel, chemin);
      // System.out.println("Tour : " + tours + " \n- Chemin : " + chemin + " \n- Capacité minimum :
      // "
      // + capaciteMinimum + "\n\n");

      for (int i = 0; i < chemin.size() - 1; i++) {
        String identifiantParent = chemin.get(i);
        String identifiantEnfant = chemin.get(i + 1);

        String identifiantArc = identifiantParent + ":" + identifiantEnfant;
        Arc arc = reseau.getArcParId(identifiantArc);

        Arc arcResiduel = reseauResiduel.getArcParId(identifiantArc);

        if (arcResiduel.getCapacite() < 0) {
          arc.setFlot(arc.getFlot() - capaciteMinimum);
        } else {
          arc.setFlot(arc.getFlot() + capaciteMinimum);
        }
      }

      reseauResiduel = new ReseauResiduel(reseau);
      chemin = this.trouverCheminCroissance(reseauResiduel);

      tours++;
    }

    Noeud puits = reseau.getPuits();
    int flotMax = 0;

    for (Arc arcFinal : puits.getArcsEntrants()) {
      flotMax += arcFinal.getFlot();
    }

    return flotMax;
  }

  public int capaciteMinimumChemin(Reseau reseau, List<String> chemin) {
    int min = Integer.MAX_VALUE;

    for (int i = 0; i < chemin.size() - 1; i++) {
      String identifiantParent = chemin.get(i);
      String identifiantEnfant = chemin.get(i + 1);

      String identifiantArc = identifiantParent + ":" + identifiantEnfant;
      Arc arc = reseau.getArcParId(identifiantArc);

      min = Math.min(min, Math.abs(arc.getCapacite()));
    }

    return min;
  }

  public List<String> trouverCheminCroissance(Reseau reseau) {
    this.visites = new ArrayList<>();
    List<String> chemin;

    for (Noeud noeud : reseau.getNoeuds()) {
      chemin = this.parcoursProfondeur(reseau, reseau.getSource(), null);

      if (chemin != null) {
        return chemin;
      }
    }

    return null;
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
