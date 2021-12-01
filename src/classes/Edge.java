package src.classes;

public class Edge {
  private int id;
  private int capacity;
  private int flow;

  private Node parent;
  private Node child;

  public Edge(int id, int capacity, Node parent, Node child) {
    this.id = id;
    this.capacity = capacity;
    this.parent = parent;
    this.child = child;
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
