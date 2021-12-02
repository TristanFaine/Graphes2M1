package src.classes;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ReseauTransport extends Reseau {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Noeud source;
  private Noeud puits;

  public ReseauTransport(int n, int m, Integer[][] A, Integer[][] B, Integer[][] PLigne,
      Integer[][] PColonne) {
    super();

    this.noeuds = new HashMap<String, Noeud>();
    this.arcs = new HashMap<String, Arc>();

    // Double boucle i,j de n a m
    // pour la création des noeuds
    // avec les valeurs de A[i][j] et B[i][j]

    // Triple condition pour créer arcs en même temps :
    // Si j > 1, alors création arcs "horizontaux", entre (i,j-1) et (i,j)
    // Si i > 1, alors création arcs "verticaux", entre (i-1,j) et (i,j)

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        String identifiantNoeud = String.valueOf(i) + "-" + String.valueOf(j);
        Noeud noeud = new Noeud(identifiantNoeud, A[i][j], B[i][j]);

        this.ajouterNoeud(noeud);
      }
    }

    // On ajoute tous les arcs pour les
    // pénalités en ligne
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m - 1; j++) {
        // couple (i,j |i+1,j) ou (i,j |i,j+1)
        String identifiantParent = String.valueOf(i) + "-" + String.valueOf(j);
        String identifiantEnfant = String.valueOf(i) + "-" + String.valueOf(j + 1);

        Noeud parent = this.getNoeudParId(identifiantParent);
        Noeud enfant = this.getNoeudParId(identifiantEnfant);

        Arc arc = new Arc(parent, enfant, PLigne[i][j]);
        Arc arcRetour = new Arc(enfant, parent, PLigne[i][j]);
        this.ajouterArc(arc);
        this.ajouterArc(arcRetour);
      }
    }

    // On ajoute tous les arcs pour les
    // pénalités en colonne
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < m; j++) {
        // couple (i,j |i+1,j) ou (i,j |i,j+1)
        String identifiantParent = String.valueOf(i) + "-" + String.valueOf(j);
        String identifiantEnfant = String.valueOf(i + 1) + "-" + String.valueOf(j);

        Noeud parent = this.getNoeudParId(identifiantParent);
        Noeud enfant = this.getNoeudParId(identifiantEnfant);

        Arc arc = new Arc(parent, enfant, PColonne[i][j]);
        Arc arcRetour = new Arc(enfant, parent, PColonne[i][j]);
        this.ajouterArc(arc);
        this.ajouterArc(arcRetour);
      }
    }

    // System.out.println(this.noeuds);
  }

  @Override
  public String toString() {
    String str = "";

    for (Noeud noeud : this.noeuds.values()) {
      str += "Noeud : " + noeud.toString() + "\n";
    }

    return str;
  }
}
