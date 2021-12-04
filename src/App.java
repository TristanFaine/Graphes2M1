package src;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import src.classes.Arc;
import src.classes.Noeud;
import src.utils.Lecteur;
import src.classes.Puits;
import src.classes.Reseau;
import src.classes.Source;
import src.classes.ReseauResiduel;
import src.classes.ReseauTransport;
import src.implementations.FordFulkerson;

public class App {

  /*
   * ConstructionReseau Une fonction qui nous permet de construire le réseau de transport à partir
   * d'un fichier de données.
   */
  static public ReseauTransport ConstructionReseau(String chemin) {
    // Lire le fichier de données
    HashMap<String, Object> donnees = new Lecteur().chargerDonnees(chemin);

    // Créer le réseau de transport
    int n = (int) donnees.get("n");
    int m = (int) donnees.get("m");

    Integer[][] A = (Integer[][]) donnees.get("A");
    Integer[][] B = (Integer[][]) donnees.get("B");

    Integer[][] PLigne = (Integer[][]) donnees.get("PLigne");
    Integer[][] PColonne = (Integer[][]) donnees.get("PColonne");

    ReseauTransport reseau = new ReseauTransport(n, m, A, B, PLigne, PColonne);

    // System.out.println(reseau);
    // En extraire les nodes & arcs
    // return new Network();

    return reseau;
  }

  // Calculer le flot max avec Ford Fulkerson
  static public HashMap<String, Object> CalculFlotMax(ReseauTransport reseau)
      throws CloneNotSupportedException {
    // TODO: Faire un type générique implémentation et charger en fonction
    // d'un parametre ?
    FordFulkerson implementation = new FordFulkerson();
    return implementation.calculFlotMax(reseau);
  }

  static public void CalculCoupeMin(ReseauTransport reseauMax) throws CloneNotSupportedException {
    ReseauResiduel reseauResiduelMax = new ReseauResiduel(reseauMax);

    FordFulkerson utilitaire = new FordFulkerson();

    // Faire un trouverChemin basenhaut false depuis la source
    // Puis get les visites
    utilitaire.trouverCheminCroissance(reseauResiduelMax, reseauResiduelMax.getSource(), false);
    List<String> partieSource = utilitaire.getVisites();

    // Faire un trouverChemin basenhaut true depuis le puits
    // puis get les visites
    utilitaire.trouverCheminCroissance(reseauResiduelMax, reseauResiduelMax.getPuits(), true);
    List<String> partiePuits = utilitaire.getVisites();

    System.out.println("Partie source : " + partieSource);
    System.out.println("\n\n\n");
    System.out.println("Partie puits : " + partiePuits);

    // imo on est cense avoir 2 ensembles totalement distincts
    // TODO: supprimer les doublons puis calculer faire une recherche sur tous les arcs qui vont
    // d'un point de l'ensemble A
    // vers un point de l'ensemble B (en checkant si parent appartient à A et enfant à B)
    // si c'est le cas sommer le flot / ou la capacite
    // et retourner un int coupeMin ou qqchose du genre

  }


  public static void main(String[] args) {
    // new Loader().load()
    // Get command line arguments
    ReseauTransport reseau = ConstructionReseau("data/instance_16_16.txt");

    try {
      HashMap<String, Object> resultatsMax = CalculFlotMax(reseau);

      int flotMax = (int) resultatsMax.get("flotMax");
      System.out.println("Flot max : " + flotMax);

      ReseauTransport reseauMax = (ReseauTransport) resultatsMax.get("reseau");
      CalculCoupeMin(reseauMax);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
