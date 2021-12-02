package src.classes;

import java.util.HashMap;

// Prend un reseau de transport dans le constructeur et retourne son reseau residuel
public class ReseauResiduel {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Noeud source;
  private Noeud puits;

  public ReseauResiduel(ReseauTransport reseau) {
    super();

    // Construire le réseau résiduel
  }
}
