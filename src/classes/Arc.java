package src.classes;

public class Arc {
  private int id;
  private int capacity;
  private int flow;

  private Noeud parent;
  private Noeud enfant;

  public Arc(int id, int capacity, Noeud parent, Noeud child) {
    this.id = id;
    this.capacity = capacity;
    this.parent = parent;
    this.enfant = child;
  }

  public int getId() {
    return id;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFlow() {
    return flow;
  }

  public void setFlow(int flow) {
    this.flow = flow;
  }


}
