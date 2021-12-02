package src.classes;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Reseau {
  private HashMap<String, Noeud> noeuds;
  private HashMap<String, Arc> arcs;
  // Les mettre dans des hashmaps comme ça on les recupere en O(1)

  private Noeud source;
  private Noeud puits;

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
    return source;
  }

  public void setSource(Noeud source) {
    this.source = source;
  }

  public Noeud getPuits() {
    return puits;
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
}
