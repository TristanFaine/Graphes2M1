package src;

import java.util.HashMap;
import src.classes.Reseau;
import src.utils.Lecteur;

public class App {

  /*
   * ConstructionReseau Une fonction qui nous permet de construire le réseau de transport à partir
   * d'un fichier de données.
   */
  static public Reseau ConstructionReseau(String chemin) {
    // Lire le fichier de données
    HashMap<String, Object> donnees = new Lecteur().chargerDonnees(chemin);

    // Créer le réseau de transport
    int n = (int) donnees.get("n");
    int m = (int) donnees.get("m");

    Integer[][] A = (Integer[][]) donnees.get("A");
    Integer[][] B = (Integer[][]) donnees.get("B");

    // En extraire les nodes & arcs
    // return new Network();

    return null;
  }


  public static void main(String[] args) {
    // new Loader().load()
    // Get command line arguments
    System.out.println("Hello, World!");
    ConstructionReseau("data/instance_16_16.txt");
  }
}
