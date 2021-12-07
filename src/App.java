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
    reseauResiduelMax.saveForVisualization();

    FordFulkerson utilitaire = new FordFulkerson();

    // Faire un trouverChemin basenhaut false depuis la source
    // Puis get les visites
    utilitaire.trouverCheminCroissance(reseauResiduelMax, reseauResiduelMax.getSource(), false);
    List<String> partieSource = utilitaire.getVisites();

    List<String> partiePuits = new ArrayList<>();
    for (Noeud noeud : reseauResiduelMax.getNoeuds()) {
      if (!partieSource.contains(noeud.getId())) {
        partiePuits.add(noeud.getId());
      }
    }

    System.out.println("Partie source : " + partieSource);
    System.out.println("\n\n\n");
    System.out.println("Partie puits : " + partiePuits);
    System.out.println("Total size : " + (partieSource.size() + partiePuits.size()));

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
    /*
     * int x = 0; int y = 0; for (String idNoeudParent : partieSource) { for (String idNoeudEnfant :
     * partiePuits) {
     * 
     * Arc potentielArc = reseauResiduelMax.getArcParId(idNoeudParent + ":" + idNoeudEnfant);
     * 
     * if (potentielArc != null) { System.out.println(potentielArc); System.out.println(
     * "Arc correspondant: " + reseauMax.getArcParId(idNoeudParent + ":" + idNoeudEnfant));
     * System.out.println("\n"); x++; } else { y++; } } }
     * 
     * System.out.println("Size partie source : " + partieSource.size() + " Size partie puits : " +
     * partiePuits.size()); System.out.println("Arcs trouvés : " + x + " Arcs non trouvés : " + y);
     * 
     * System.out.println("Source"); System.out.println(reseauResiduelMax.getSource());
     * 
     * System.out.println("\n\n"); System.out.println("Puits");
     * System.out.println(reseauResiduelMax.getPuits());
     * 
     * System.out.println("\n\n\n"); System.out.println("0-3");
     * System.out.println(reseauResiduelMax.getNoeudParId("0-3"));
     */
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

    if (args.length > 0) {
      List<String> arguments = Arrays.asList(args);
      StringBuilder path = new StringBuilder();

      for (String argument : arguments) {
        path.append(argument);
      }

      System.out.println("Chargement des données depuis le fichier : " + path.toString());
      ReseauTransport reseau = ConstructionReseau(path.toString());

      try {
        HashMap<String, Object> resultatsMax = CalculFlotMax(reseau);

        int flotMax = (int) resultatsMax.get("flotMax");
        System.out.println("Flot max : " + flotMax);

        ReseauTransport reseauMax = (ReseauTransport) resultatsMax.get("reseau");
        CalculCoupeMin(reseauMax);
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Usage: java -jar BinarisationImage.jar <path-vers-fichier-donnees>");
    }


  }
}
