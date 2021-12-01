package src.classes;

import java.util.List;

public class Network {
  private List<Node> nodes;
  private List<Edge> edges;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Node source;
  private Node sink;

  public Network(List<Node> nodes, List<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public Node getSource() {
    return source;
  }

  public void setSource(Node source) {
    this.source = source;
  }

  public Node getSink() {
    return sink;
  }

  public void setSink(Node sink) {
    this.sink = sink;
  }

  public void addNode(Node node) {
    nodes.add(node);
  }

  public void addEdge(Edge edge) {
    edges.add(edge);
  }

  // get node by id
  public Node getNodeById(int id) {
    for (Node node : nodes) {
      if (node.getId() == id) {
        return node;
      }
    }

    return null;
  }

  // get edge by id
  public Edge getEdgeById(int id) {
    for (Edge edge : edges) {
      if (edge.getId() == id) {
        return edge;
      }
    }

    return null;
  }

  public void ForkFulkerson() {

  }
}
