package src.classes;

import java.util.ArrayList;
import java.util.List;

public class Node {
  private List<Edge> inboundEdges;
  private List<Edge> outboundEdges;

  private int id;
  private int a;
  private int b;

  private boolean source;
  private boolean sink;

  public Node(int id, int a, int b) {
    this.id = id;
    this.a = a;
    this.b = b;
    this.inboundEdges = new ArrayList<Edge>();
    this.outboundEdges = new ArrayList<Edge>();
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
