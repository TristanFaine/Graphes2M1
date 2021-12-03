package src;

import java.util.HashMap;
import src.classes.Puits;
import src.classes.Reseau;
import src.classes.ReseauTransport;
import src.classes.Source;
import src.implementations.FordFulkerson;
import src.utils.Lecteur;

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
  static public void CalculFlotMax(ReseauTransport reseau) throws CloneNotSupportedException {
    // TODO: Faire un type générique implémentation et charger en fonction
    // d'un parametre ?
    FordFulkerson implementation = new FordFulkerson();
    implementation.calculFlotMax(reseau);
  }


  public static void main(String[] args) {
    // new Loader().load()
    // Get command line arguments
    ReseauTransport reseau = ConstructionReseau("data/instance_16_16.txt");

    try {
      CalculFlotMax(reseau);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
