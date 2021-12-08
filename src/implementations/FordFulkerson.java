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
    // Commencer à partir de la source:
    // Tant qu'il existe un arc sortant avec flot < capacité:
    // Passer la sauce (ajouter val flot)

    // Ou plutôt :
    // Si existance un chemin de croissance (avoir réseau résiduel?)
    // Ajouter val flot le plus petit sur chaque arc (faire -val si arc retour)

    //Note pour lune on cherche flot 1025 mais on a flot 1125, c'est le bordel
    //934 tours, beaucoup de capa minimale=1 (normal)
    //on dirait que le "contour" des lunes est respecté, mais pas l'intérieur...
    //ptete un truc où il faut changer basenHaut avec condition..?
    //ou alors un tri implicite en jdk8+?

    //On a nos coupe en...
    //non pas important, déja le flot max est incorrect, donc l'erreur est ici.
    //en regardant le dump de l'execution, j'ai l'impression que l'on "entre" dans certains parcours à côté d'une vraie séparation plan 1 plan 2

    //OH OHH ATTENDS.
    //SI ON TRIE PAR ENFANT, FORD FULK NE PASSE PLUS PAR CERTAINS SOMMETS (NORMAL)
    //je crois etre sur le point de eureka
    int tours = 0;
    //Certains tours se répétent... pourquoi..?

    ReseauResiduel reseauResiduel = new ReseauResiduel(reseau);
    List<String> chemin = this.trouverCheminCroissance(reseauResiduel, null, false);

    while (chemin != null) {
      int capaciteMinimum = this.capaciteMinimumChemin(reseauResiduel, chemin);
      //System.out.println("Tour : " + tours + " \n- Chemin : " + chemin + " \n- Capacité minimum :"+ capaciteMinimum + "\n\n");

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

    //dans chaque noeud du reseau faire un parcours en profondeur depuis le noeud source
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
    //Ok apparament y'a quelque chose ici qui fait que java se #### dessus en jdk 7 plutot que jdk 17.
    if (chemin == null) {
      chemin = new ArrayList<>();
    }

    // Pour éviter les doublons lors du min-cut
    if (!this.visites.contains(noeud.getId())) {
      this.visites.add(noeud.getId());
    }
    //System.out.println(this.visites);

    chemin.add(noeud.getId());

    // Si on fait la recherche à l'envers
    List<Arc> arcsExplorables = basEnHaut ? noeud.getArcsEntrants() : noeud.getArcsSortants();

    //Essayons de lister ces arcs dans l'ordre avant de faire des trucs
    //ok si on cherche à trier dans un "ordre"... ça fera beaucoup de traitement...
    //Testons un truc bizarre, prenons dans un ordre decroissant à partir de destination parent -> enfant.
    
    
    //RAHHHHHHHHHHHHHH MEME EN CHANGEANT LES TRIS, L'ORDRE, LA SEQUENCE, LE COTE ABSOLU OU NON LES JDK DONNENT DES RESULTATS DIFFERENTS

    //on voudrait genre.. d'abord rentrer par truc source -> sommet de taille elevée, pour prendre la capacité minimale entre les trucs où la pénalité pour séparer est elevée

    //Essayons un truc : filtrer par capacité la plus grande (pour avoir SOURCE -> X elevé, pour rentrer dans un chemin)
    //Par contre, si on a (X -> puits), alors filtrer par valeur basse (dés qu'on "sort" du sous-groupe, se mettre dans le puits)

    //aller truc pas performant, voir si nom est source, sinon faire autre tri
    //System.out.println("beforesort");
    //System.out.println(arcsExplorables);

    //Logique... source passe d'abord voir les "sommets d'entrée sous groupes, genre ceux qui sont quasi-sûr d'etre au deuxieme plan"
    /*
    *
    *
    *
    */
    if (arcsExplorables.get(0).getParent().getId() == "source") {
      Collections.sort(arcsExplorables, new Comparator<Arc>(){
        public int compare(Arc a1, Arc a2) {
          int c;
          c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1
           : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1
           : 0;
          if (c == 0)
          c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
          return c;

        }
      });
    } //ok j'ai le truc pour ma premiere iteration.. ensuite comment faire pour "si tout ses voisins ont été parcourus alors prendre puits"..?
     else { //Quand on est dans un "sous groupe", il faut ensuite essayer d'aller se connecter à autant de voisins que possible
      //System.out.println(arcsExplorables.size());
      //Essayons force brute extreme :
      //on passe d'abord par les source => faible ou fort jsp pour ""magie"""
      /*
      * Pour a faible (disons a < b) : 
      * Prendre voisins où (a<b) également
      * Pour b fort (a > b) :
      * Prendre voisins où (a > b)
      */
      //System.out.println(arcsExplorables.get(0).getParent().getA());
      if (arcsExplorables.get(0).getParent().getA() < arcsExplorables.get(0).getParent().getB()) {
        Collections.sort(arcsExplorables, new Comparator<Arc>(){
          //attends wait, et si on mettait les resultats retour à la fin?
          public int compare(Arc a1, Arc a2) {
            int c;
            c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? 1
             : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? -1
             : 0;
            if (c == 0)
            c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
            return c;
  
          }
        });
      } else {
        Collections.sort(arcsExplorables, new Comparator<Arc>(){
          //attends wait, et si on mettait les resultats retour à la fin?
          public int compare(Arc a1, Arc a2) {
            int c;
            c = Math.abs(a1.getCapacite()) < Math.abs(a2.getCapacite()) ? -1
             : Math.abs(a1.getCapacite()) > Math.abs(a2.getCapacite()) ? 1
             : 0;
            if (c == 0)
            c = a1.getEnfant().getId().compareTo(a2.getEnfant().getId());
            return c;
  
          }
        });
      }
      
    }
    //System.out.println("aftersort");
    //System.out.println(arcsExplorables);

    for (Arc arcSortant : arcsExplorables) {
      //System.out.println(arcSortant);
      Noeud voisin = arcSortant.getEnfant();

      //S'arrêter quand on atteint la fin du chemin
      if ((!basEnHaut && voisin.getId() == "puits") || (basEnHaut && voisin.getId() == "source")) {
        chemin.add("puits");
        return chemin;
      }

      //Passer au voisin suivant
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
