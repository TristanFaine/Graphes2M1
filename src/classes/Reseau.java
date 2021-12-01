package src.classes;

import java.util.List;

public class Reseau {
  private List<Noeud> noeuds;
  private List<Arc> arcs;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Noeud source;
  private Noeud sink;

  public Reseau(List<Noeud> noeuds, List<Arc> arcs) {
    this.noeuds = noeuds;
    this.arcs = arcs;
  }

  public List<Noeud> getNodes() {
    return noeuds;
  }

  public List<Arc> getEdges() {
    return arcs;
  }

  public Noeud getSource() {
    return source;
  }

  public void setSource(Noeud source) {
    this.source = source;
  }

  public Noeud getSink() {
    return sink;
  }

  public void setSink(Noeud sink) {
    this.sink = sink;
  }

  public void addNode(Noeud node) {
    noeuds.add(node);
  }

  public void addEdge(Arc edge) {
    arcs.add(edge);
  }

  // get node by id
  public Noeud getNodeById(int id) {
    for (Noeud node : noeuds) {
      if (node.getId() == id) {
        return node;
      }
    }

    return null;
  }

  // get edge by id
  public Arc getEdgeById(int id) {
    for (Arc edge : arcs) {
      if (edge.getId() == id) {
        return edge;
      }
    }

    return null;
  }

  public void ForkFulkerson() {

  }
}
