package src.classes;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Reseau {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Noeud source;
  private Noeud puits;

  // TODO: Create sink & source in constructor
  // and add edge when constructing graph
  // to avoid re-looping on every node later

  public Reseau() {
    this.noeuds = new HashMap<String, Noeud>();
    this.arcs = new HashMap<String, Arc>();
    this.source = null;
    this.puits = null;
  }

  @Override
  public String toString() {
    String str = "";

    for (Noeud noeud : this.noeuds.values()) {
      str += "Noeud : " + noeud.toString() + "\n";
    }

    return str;
  }

  public List<Noeud> getNoeuds() {
    return new ArrayList<Noeud>(noeuds.values());
  }

  public List<Arc> getArcs() {
    return new ArrayList<Arc>(arcs.values());
  }

  public Noeud getSource() {
    return this.source;
  }

  public void setSource(Noeud source) {
    this.source = source;
  }

  public Noeud getPuits() {
    return this.puits;
  }

  public void setPuits(Noeud puits) {
    this.puits = puits;
  }

  public void ajouterNoeud(Noeud noeud) {
    this.noeuds.put(noeud.getId(), noeud);
  }

  public void ajouterArc(Arc arc) {
    this.arcs.put(arc.getId(), arc);
  }

  // get node by id
  public Noeud getNoeudParId(String id) {
    return this.noeuds.get(id);
  }

  // get edge by id
  public Arc getArcParId(String id) {
    return this.arcs.get(id);
  }

  public void saveForVisualization() {
    List<String> textFile = new ArrayList<String>();

    for (Noeud noeud : this.getNoeuds()) {
      textFile.add("ID=" + noeud.getId() + " a=" + noeud.getA() + " b=" + noeud.getB());
    }

    for (Arc arc : this.getArcs()) {
      textFile.add("ID=" + arc.getId() + " parent=" + arc.getParent().getId() + " enfant="
          + arc.getEnfant().getId() + " flot=" + arc.getFlot() + " capacite=" + arc.getCapacite());
    }

    try {
      // Write lines of text to a file.
      Path file = Paths.get("./graph.txt");
      Files.write(file, textFile, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
