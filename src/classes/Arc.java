package src.classes;

public class Arc {
  private String id;
  private int capacite;
  private int flot;

  private Noeud parent;
  private Noeud enfant;

  public Arc(Noeud parent, Noeud enfant, int capacite) {
    // System.out.println(parent);
    // System.out.println(enfant);
    // System.out.println(parent.getId() + ":" + enfant.getId());
    this.id = parent.getId() + ":" + enfant.getId();
    this.capacite = capacite;
    this.parent = parent;
    this.enfant = enfant;

    this.parent.ajouterArcSortant(this);
    this.enfant.ajouterArcEntrant(this);
  }

  @Override
  public String toString() {
    return "Arc [id=" + this.id + ", capacite=" + this.capacite + ", flot=" + this.flot + "]\n";
  }

  public String getId() {
    return this.id;
  }

  public int getCapacite() {
    return this.capacite;
  }

  public int getFlot() {
    return this.flot;
  }

  public void setFlot(int flot) {
    this.flot = flot;
  }
}
