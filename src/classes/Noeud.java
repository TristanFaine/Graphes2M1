package src.classes;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
  private List<Arc> arcsEntrants;
  private List<Arc> arcsSortants;

  private String id;
  private int a;
  private int b;

  private boolean source;
  private boolean puits;

  public Noeud(String id, int a, int b) {
    this.id = id;
    this.a = a;
    this.b = b;
    this.arcsEntrants = new ArrayList<Arc>();
    this.arcsSortants = new ArrayList<Arc>();
  }

  @Override
  public String toString() {
    String str = "[ID=" + this.id + " a=" + this.a + " b=" + this.b + "]\n";

    if (this.arcsEntrants.size() > 0) {
      str += "Arcs entrants: \n";
      for (Arc arc : this.arcsEntrants) {
        str += "-" + arc.toString();
      }
    }

    if (this.arcsSortants.size() > 0) {
      str += "\nArcs sortants: \n";
      for (Arc arc : this.arcsSortants) {
        str += "-" + arc.toString();
      }
    }

    return str;
  }

  public String getId() {
    return this.id;
  }

  public int getA() {
    return this.a;
  }

  public int getB() {
    return this.b;
  }

  public void setA(int a) {
    this.a = a;
  }

  public void setB(int b) {
    this.b = b;
  }

  public void ajouterArcEntrant(Arc arc) {
    this.arcsEntrants.add(arc);
  }

  public void ajouterArcSortant(Arc arc) {
    this.arcsSortants.add(arc);
  }
}
