package src.classes;

// Cette classe nous permet de différencier un arc aller d'un arc retour
// dans le réseau résiduel
public class ArcRetour extends Arc {

  public ArcRetour(Noeud parent, Noeud enfant, int capacite) {
    super(parent, enfant, capacite);
  }
}
