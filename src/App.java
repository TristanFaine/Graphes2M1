package src;

import java.util.List;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    // En extraire les nodes & arcs
    // return new Network();

    return reseau;
  }

  // Calculer le flot max avec Ford Fulkerson
  static public HashMap<String, Object> CalculFlotMax(ReseauTransport reseau)
      throws CloneNotSupportedException {
    FordFulkerson implementation = new FordFulkerson();
    return implementation.calculFlotMax(reseau);
  }

  

  static public void CalculCoupeMin(ReseauTransport reseauMax) throws CloneNotSupportedException {
    ReseauResiduel reseauResiduelMax = new ReseauResiduel(reseauMax);
    reseauResiduelMax.saveForVisualization();

    FordFulkerson utilitaire = new FordFulkerson();

    // Faire un trouverChemin basenhaut false depuis la source
    // Puis get les visites
    utilitaire.trouverCheminCroissance(reseauResiduelMax, reseauResiduelMax.getSource(), false);
    List<String> partieSource = utilitaire.getVisites();

    List<String> partiePuits = new ArrayList<String>();
    for (Noeud noeud : reseauResiduelMax.getNoeuds()) {
      if (!partieSource.contains(noeud.getId())) {
        partiePuits.add(noeud.getId());
      }
    }
    partieSource.remove("source");
    partiePuits.remove("puits");

    System.out.println("Premier plan : " + partieSource);
    System.out.println("\n");
    System.out.println("Second plan : " + partiePuits);

    List<String> textFile = new ArrayList<String>();

    for (Object x : partieSource) {
      textFile.add("Plan1:" + x.toString());
    }

    for (Object x : partiePuits) {
      textFile.add("Plan2:" + x.toString());
    }

    try {
      // Write lines of text to a file.
      Path file = Paths.get("./graph.txt");
      Files.write(file, textFile, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static public void ResoudreBinMin(String filepath) {
    System.out.println("Chargement des données depuis le fichier : " + filepath.toString());
    ReseauTransport reseau = ConstructionReseau(filepath.toString());

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
  public static void main(String[] args) {
    // new Loader().load()
    // Get command line arguments
    if (args.length > 0) {
      List<String> arguments = Arrays.asList(args);
      StringBuilder path = new StringBuilder();

      for (String argument : arguments) {
        path.append(argument);
      }

      ResoudreBinMin(path.toString()); 
    }
    else {
      System.out.println("Usage: java -jar BinarisationImage.jar <path-vers-fichier-donnees>");
    }


  }
}
