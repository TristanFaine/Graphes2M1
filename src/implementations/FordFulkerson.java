package src.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
  public HashMap<String, Object> calculFlotMax(ReseauTransport reseau)
      throws CloneNotSupportedException {

    // Ou plutôt :
    // Si il existe un chemin de croissance (avoir réseau résiduel?)
    // Ajouter valeur flot le plus petit sur chaque arc (faire -val si arc retour)

    int tours = 0;

    ReseauResiduel reseauResiduel = new ReseauResiduel(reseau);
    List<String> chemin = this.trouverCheminCroissance(reseauResiduel, null, false);

    while (chemin != null) {
      int capaciteMinimum = this.capaciteMinimumChemin(reseauResiduel, chemin);

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
      chemin = this.trouverCheminCroissance(reseauResiduel, null, false);

      tours++;
    }

    Noeud puits = reseau.getPuits();
    int flotMax = 0;

    for (Arc arcFinal : puits.getArcsEntrants()) {
      flotMax += arcFinal.getFlot();
    }

    HashMap<String, Object> results = new HashMap<String, Object>();
    results.put("flotMax", flotMax);
    results.put("reseau", reseau);

    return results;
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

  public List<String> trouverCheminCroissance(Reseau reseau, Noeud depart, boolean basEnHaut) {
    this.visites = new ArrayList<>();
    List<String> chemin;

    if (depart == null) {
      depart = reseau.getSource();
    }

    // dans chaque noeud du reseau faire un parcours en profondeur depuis le noeud source
    for (Noeud noeud : reseau.getNoeuds()) {
      chemin = this.parcoursProfondeur(reseau, depart, null, basEnHaut);

      if (chemin != null) {
        return chemin;
      }
    }

    return null;
  }

  public List<String> parcoursProfondeur(Reseau reseau, Noeud noeud, List<String> chemin,
      boolean basEnHaut) {
    if (chemin == null) {
      chemin = new ArrayList<>();
    }

    // Pour éviter les doublons lors du min-cut
    if (!this.visites.contains(noeud.getId())) {
      this.visites.add(noeud.getId());
    }

    chemin.add(noeud.getId());

    // Si on fait la recherche à l'envers
    List<Arc> arcsExplorables = basEnHaut ? noeud.getArcsEntrants() : noeud.getArcsSortants();

    if (arcsExplorables.size() > 0) {
      if (arcsExplorables.get(0).getParent().getId() == "source") {
        Collections.sort(arcsExplorables, new Comparator<Arc>() {
          public int compare(Arc a1, Arc a2) {
            int c;
            c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1
                : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1 : 0;
            if (c == 0)
              c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
            return c;

          }
        });
      } else {
        if (arcsExplorables.get(0).getParent().getA() < arcsExplorables.get(0).getParent().getB()) {
          Collections.sort(arcsExplorables, new Comparator<Arc>() {
            public int compare(Arc a1, Arc a2) {
              int c;
              c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? 1
                  : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? -1 : 0;
              if (c == 0)
                c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
              return c;

            }
          });
        } else {
          Collections.sort(arcsExplorables, new Comparator<Arc>() {
            public int compare(Arc a1, Arc a2) {
              int c;
              c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1
                  : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1 : 0;
              if (c == 0)
                c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
              return c;

            }
          });
        }

      }
    }


    for (Arc arcSortant : arcsExplorables) {
      Noeud voisin = arcSortant.getEnfant();

      // S'arrêter quand on atteint la fin du chemin
      if ((!basEnHaut && voisin.getId() == "puits") || (basEnHaut && voisin.getId() == "source")) {
        chemin.add("puits");
        return chemin;
      }

      // Passer au voisin suivant
      if (!this.visites.contains(voisin.getId())) {
        return this.parcoursProfondeur(reseau, voisin, chemin, basEnHaut);
      }
    }

    return null;
  }

  public List<String> getVisites() {
    return this.visites;
  }
}
