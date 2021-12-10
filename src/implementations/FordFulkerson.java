package src.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import src.classes.Arc;
import src.classes.ArcRetour;
import src.classes.Noeud;
import src.classes.Reseau;
import src.classes.ReseauResiduel;
import src.classes.ReseauTransport;

public class FordFulkerson {
  private List<String> visites;

  public List<String> getVisites() {
    return this.visites;
  }

  public HashMap<String, Object> calculFlotMax(ReseauTransport reseau)
      throws CloneNotSupportedException {
    // On recupere un reseau de transport
    // On veut maximiser son flot
    // a l'aide de l'algorithme de Ford Fulkerson

    // pour cela on effectue les etapes suivantes

    // On calcule le réseau résiduel à partir
    // de notre réseau de transport

    // tant qu'il est possible de trouver un chemin de la source
    // vers le puits dans le réseau résiduel

    // On identifie le chemin et on calcule la capacité minimum du chemin

    // Puis pour chaque arc du chemin
    // On met à jour dans le réseau de transport
    // le flot sur l'arc correspondant en y ajoutant
    // ou retirant la capacité minimum du chemin

    // On cherche d'abord un premier chemin de la source
    // vers le puits dans le chemin résiduel
    ReseauResiduel reseauResiduel = new ReseauResiduel(reseau);
    List<String> chemin = this.trouverChemin(reseauResiduel);

    // Tant qu'on est capable de trouver un chemin entre
    // la source et le puits
    while (chemin != null) {
      // On calcule la capacite minimum
      // sur le chemin dans le reseau residuel

      // Pour cela on va parcourir le chemin
      // recuperer l'arc entre chaque noeuds
      // et calculer la capacite minimum sur l'arc
      int capaciteMinimum = Integer.MAX_VALUE;

      for (int i = 0; i < chemin.size() - 1; i++) {
        // On recupere l'arc entre les noeuds
        String identifiantArc = chemin.get(i) + ":" + chemin.get(i + 1);
        Arc arc = reseauResiduel.getArcParId(identifiantArc);

        // On met à jour la capacité minimum
        capaciteMinimum = Math.min(capaciteMinimum, arc.getCapacite());
      }

      // On met à jour le flot sur chaque arc du chemin
      // en y ajoutant ou en y retirant la capacité minimum
      // en fonction de si l'arc un arc simple ou un arc retour
      for (int i = 0; i < chemin.size() - 1; i++) {
        // On recupere l'arc entre les noeuds
        String identifiantArc = chemin.get(i) + ":" + chemin.get(i + 1);

        Arc arcResiduel = reseauResiduel.getArcParId(identifiantArc);
        Arc arcTransport = reseau.getArcParId(identifiantArc);

        // On met à jour le flot sur l'arc dans le reseau
        // initial de transport

        // Tester si le type de arcResiduel == ArcRetour
        if (arcResiduel instanceof ArcRetour) {
          arcTransport.setFlot(arcTransport.getFlot() - capaciteMinimum);
        } else {
          arcTransport.setFlot(arcTransport.getFlot() + capaciteMinimum);
        }
      }

      // Puis on calcule un nouveau reseauResiduel
      // apres la mise a jour du flot dans le reseau
      // de transport et on y cherche un nouveau chemin
      reseauResiduel = new ReseauResiduel(reseau);
      chemin = this.trouverChemin(reseauResiduel);
    }

    // On calcule le flot max en faisant la somme
    // de tous le flot sur les arcs entrants
    // du puits dans le reseau de transport
    int flotMax = 0;

    for (Arc arc : reseau.getPuits().getArcsEntrants()) {
      flotMax += arc.getFlot();
    }

    HashMap<String, Object> results = new HashMap<String, Object>();
    results.put("flotMax", flotMax);
    results.put("reseau", reseau);

    return results;
  }

  // Recherche d'un chemin entre la source et le puits
  // dans le réseau résiduel par recherche en profondeur
  // On retourne une liste de noeuds
  public List<String> trouverChemin(ReseauResiduel reseau) {
    // On reinitialise la liste des visites
    this.visites = new ArrayList<String>();

    List<String> chemin = new ArrayList<String>();
    HashMap<String, String> predecesseurs = new HashMap<String, String>();

    Boolean puitsAtteint = false;

    // On fait une recherche en profondeur
    // iterative a l'aide d'une pile
    List<Noeud> pile = new ArrayList<Noeud>();
    pile.add(reseau.getSource());

    // Tant que la pile n'est pas vide
    while (pile.size() > 0 && !puitsAtteint) {
      // On ajoute les enfants du noeuds si on ne les
      // a pas encore visités

      // Le noeud qu'on choisit est le premier noeud
      // de la pile (i.e le noeud visite le plus recemment)
      Noeud noeud = pile.get(0);

      // On retire le noeud de la pile
      pile.remove(0);

      // On ajoute le noeud dans la liste des visites
      if (!this.visites.contains(noeud.getId())) {
        this.visites.add(noeud.getId());
      }

      // On ajoute les enfants du noeud dans la pile
      // Pour cela on recupere les arcs sortants
      for (Arc arc : noeud.getArcsSortants()) {
        // Si l'enfant de l'arc est le puits, la recherche est finie
        if (arc.getEnfant().getId().equals(reseau.getPuits().getId())) {
          predecesseurs.put(arc.getEnfant().getId(), noeud.getId());
          puitsAtteint = true;
        }

        // Sinon pour chaque arc on regarde si l'enfant a ete visite ou non
        else if (!this.visites.contains(arc.getEnfant().getId())) {
          predecesseurs.put(arc.getEnfant().getId(), noeud.getId());
          // On ajoute le noeud dans la pile
          pile.add(arc.getEnfant());
        }
      }
    }

    if (puitsAtteint) {
      chemin.add("puits");

      while (!chemin.contains("source")) {
        chemin.add(predecesseurs.get(chemin.get(chemin.size() - 1)));
      }

      Collections.reverse(chemin);
      return chemin;
    } else {
      return null;
    }
  }



  /*
   * private List<String> visites;
   * 
   * // Return le reseau avec son flot maximal // Algorithme de Ford Fulkerson public
   * HashMap<String, Object> calculFlotMax(ReseauTransport reseau) throws CloneNotSupportedException
   * {
   * 
   * // Ou plutôt : // Si il existe un chemin de croissance (avoir réseau résiduel?) // Ajouter
   * valeur flot le plus petit sur chaque arc (faire -val si arc retour)
   * 
   * int tours = 0;
   * 
   * ReseauResiduel reseauResiduel = new ReseauResiduel(reseau); List<String> chemin =
   * this.trouverCheminCroissance(reseauResiduel, null, false);
   * 
   * while (chemin != null) { int capaciteMinimum = this.capaciteMinimumChemin(reseauResiduel,
   * chemin);
   * 
   * for (int i = 0; i < chemin.size() - 1; i++) { String identifiantParent = chemin.get(i); String
   * identifiantEnfant = chemin.get(i + 1);
   * 
   * String identifiantArc = identifiantParent + ":" + identifiantEnfant; Arc arc =
   * reseau.getArcParId(identifiantArc);
   * 
   * Arc arcResiduel = reseauResiduel.getArcParId(identifiantArc);
   * 
   * if (arcResiduel.getCapacite() < 0) { arc.setFlot(arc.getFlot() - capaciteMinimum); } else {
   * arc.setFlot(arc.getFlot() + capaciteMinimum); } }
   * 
   * reseauResiduel = new ReseauResiduel(reseau); chemin =
   * this.trouverCheminCroissance(reseauResiduel, null, false);
   * 
   * tours++; }
   * 
   * Noeud puits = reseau.getPuits(); int flotMax = 0;
   * 
   * for (Arc arcFinal : puits.getArcsEntrants()) { flotMax += arcFinal.getFlot(); }
   * 
   * HashMap<String, Object> results = new HashMap<String, Object>(); results.put("flotMax",
   * flotMax); results.put("reseau", reseau);
   * 
   * return results; }
   * 
   * public int capaciteMinimumChemin(Reseau reseau, List<String> chemin) { int min =
   * Integer.MAX_VALUE;
   * 
   * for (int i = 0; i < chemin.size() - 1; i++) { String identifiantParent = chemin.get(i); String
   * identifiantEnfant = chemin.get(i + 1);
   * 
   * String identifiantArc = identifiantParent + ":" + identifiantEnfant; Arc arc =
   * reseau.getArcParId(identifiantArc); min = Math.min(min, Math.abs(arc.getCapacite())); }
   * 
   * return min; }
   * 
   * public List<String> trouverCheminCroissance(Reseau reseau, Noeud depart, boolean basEnHaut) {
   * this.visites = new ArrayList<>(); List<String> chemin;
   * 
   * if (depart == null) { depart = reseau.getSource(); }
   * 
   * // dans chaque noeud du reseau faire un parcours en profondeur depuis le noeud source for
   * (Noeud noeud : reseau.getNoeuds()) { chemin = this.parcoursProfondeur(reseau, depart, null,
   * basEnHaut);
   * 
   * if (chemin != null) { return chemin; } }
   * 
   * return null; }
   * 
   * public List<String> parcoursProfondeur(Reseau reseau, Noeud noeud, List<String> chemin, boolean
   * basEnHaut) { if (chemin == null) { chemin = new ArrayList<>(); }
   * 
   * // Pour éviter les doublons lors du min-cut if (!this.visites.contains(noeud.getId())) {
   * this.visites.add(noeud.getId()); }
   * 
   * chemin.add(noeud.getId());
   * 
   * // Si on fait la recherche à l'envers List<Arc> arcsExplorables = basEnHaut ?
   * noeud.getArcsEntrants() : noeud.getArcsSortants();
   * 
   * if (arcsExplorables.size() > 0) { if (arcsExplorables.get(0).getParent().getId() == "source") {
   * Collections.sort(arcsExplorables, new Comparator<Arc>() { public int compare(Arc a1, Arc a2) {
   * int c; c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1 :
   * Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1 : 0; if (c == 0) c =
   * a1.getEnfant().getId().compareTo(a2.getEnfant().getId()); return c;
   * 
   * } }); } else { if (arcsExplorables.get(0).getParent().getA() <
   * arcsExplorables.get(0).getParent().getB()) { Collections.sort(arcsExplorables, new
   * Comparator<Arc>() { public int compare(Arc a1, Arc a2) { int c; c = Math.abs(a1.getCapacite())
   * < Math.abs(a2.getCapacite()) ? 1 : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? -1
   * : 0; if (c == 0) c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId()); return c;
   * 
   * } }); } else { Collections.sort(arcsExplorables, new Comparator<Arc>() { public int compare(Arc
   * a1, Arc a2) { int c; c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1 :
   * Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1 : 0; if (c == 0) c =
   * a1.getEnfant().getId().compareTo(a2.getEnfant().getId()); return c;
   * 
   * } }); }
   * 
   * } }
   * 
   * 
   * for (Arc arcSortant : arcsExplorables) { Noeud voisin = arcSortant.getEnfant();
   * 
   * // S'arrêter quand on atteint la fin du chemin if ((!basEnHaut && voisin.getId() == "puits") ||
   * (basEnHaut && voisin.getId() == "source")) { chemin.add("puits"); return chemin; }
   * 
   * // Passer au voisin suivant if (!this.visites.contains(voisin.getId())) { return
   * this.parcoursProfondeur(reseau, voisin, chemin, basEnHaut); } }
   * 
   * return null; }
   * 
   * public List<String> getVisites() { return this.visites; }
   */
}
