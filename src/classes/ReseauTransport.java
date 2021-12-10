package src.classes;

import java.util.HashMap;

public class ReseauTransport extends Reseau {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;

  private Noeud source;
  private Noeud puits;

  public ReseauTransport(int n, int m, Integer[][] A, Integer[][] B, Integer[][] PLigne,
      Integer[][] PColonne) {
    super();

    this.noeuds = new HashMap<String, Noeud>();
    this.arcs = new HashMap<String, Arc>();

    new Source(this);
    new Puits(this);

    // Double boucle i,j de n a m
    // pour la création des noeuds
    // avec les valeurs de A[i][j] et B[i][j]

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        String identifiantNoeud = String.valueOf(i) + "-" + String.valueOf(j);
        Noeud noeud = new Noeud(identifiantNoeud, A[i][j], B[i][j]);

        this.ajouterNoeud(noeud);

        Arc arcSource = new Arc(this.getSource(), noeud, A[i][j]);
        Arc arcPuits = new Arc(noeud, this.getPuits(), B[i][j]);

        this.ajouterArc(arcSource);
        this.ajouterArc(arcPuits);
      }
    }

    // On ajoute tous les arcs pour les
    // pénalités en ligne
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m - 1; j++) {
        // couple (i,j |i,j+1)
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
        // couple (i,j |i+1,j)
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
  }
}
