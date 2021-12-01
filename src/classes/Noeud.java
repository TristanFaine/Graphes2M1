package src.classes;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
  private List<Arc> arcsEntrants;
  private List<Arc> arcsSortants;

  private int id;
  private int a;
  private int b;

  private boolean source;
  private boolean puit;

  public Noeud(int id, int a, int b) {
    this.id = id;
    this.a = a;
    this.b = b;
    this.arcsEntrants = new ArrayList<Arc>();
    this.arcsSortants = new ArrayList<Arc>();
  }

  public int getId() {
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
}
