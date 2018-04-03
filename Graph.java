/*
* File: Graph.java
* Author: Claire Stovall
* Date: February 5, 2018
* Purpose: This class defines the directed graph for the program that behaves
* like the Java command line compiler.
*/

import java.util.*;

// Generic class to define the directed graph
public class Graph<T> {
  // Array list of vertices
  private ArrayList<Vertex> arrayList;
  // Used to associate vertex names with their index in the list of vertices
  private HashMap<T, Integer> hashMap;
  private Vertex value;
  private StringBuilder output;
  private int key;
  private Stack<String> stack;

  // Constructor to initialize the graph
  public Graph() {
    arrayList = new ArrayList<Vertex>();
    hashMap = new HashMap<T, Integer>();
    key = 0;
  }

  // Method to add an edge to the graph
  public void addEdge(T vertex1, T vertex2) {
    if (!hashMap.containsKey(vertex1)) {
      this.addVertex(vertex1);
    }
    if (!hashMap.containsKey(vertex2)) {
      this.addVertex(vertex2);
    }
    value = arrayList.get(hashMap.get(vertex1));
    value.addNeighbor(hashMap.get(vertex2));
  }

  // Method to add a vertex in order to add an edge
  public void addVertex(T newVertex) {
    hashMap.put(newVertex, key);
    arrayList.add(key, new Vertex(newVertex));
    key++;
  }

  // Method to generate a topological ordering given a starting vertex
  // based on DFS
  public String generateToplogicalOrder(T startingPoint)
      throws InvalidClassException, CycleDetectedException{
    if (!hashMap.containsKey(startingPoint)) {
      throw new InvalidClassException();
    }
    stack = new Stack<String>();
    output = new StringBuilder();
    for (Vertex i : arrayList) {
      i.setDiscovered(false);
      i.setFinished(false);
    }
    this.depthFirstSearch(hashMap.get(startingPoint));
    while (!stack.empty()) {
      output.append(stack.pop() + " ");
    }
    return output.toString();
  }

  // Depth first search method
  public void depthFirstSearch(int startingPoint)
      throws CycleDetectedException {
    if (arrayList.get(startingPoint).getDiscovered()) {
      throw new CycleDetectedException();
    }
    if (arrayList.get(startingPoint).getFinished()) {
      return;
    }
    arrayList.get(startingPoint).setDiscovered(true);
    for (int i = 0; i < arrayList.get(startingPoint).getList().size(); i++) {
      depthFirstSearch((Integer) arrayList.get(startingPoint).getList().get(i));
    }
    arrayList.get(startingPoint).setFinished(true);
    stack.push(arrayList.get(startingPoint).toString());
  }

  // Class to define Vertex objects
  private class Vertex {
    // Linked lists of integers that represent the index
    // rather than vertex name itself
    private LinkedList<Integer> linkedList;
    // Generic type for the vertex names
    private T name;
    private boolean discovered;
    private boolean finished;

    // Constructor
    public Vertex(T name) {
      this.name = name;
      linkedList = new LinkedList<Integer>();
      discovered = false;
      finished = false;
    }

    // Method to add a neighboring vertex
    public void addNeighbor(int neighbor) {
      linkedList.add(neighbor);
    }

    // Getter
    public LinkedList getList() {
      return linkedList;
    }

    // Setter
    public void setDiscovered(boolean discovered) {
      this.discovered = discovered;
    }

    // Getter
    public boolean getDiscovered() {
      return discovered;
    }

    // Setter
    public void setFinished(boolean finished) {
      this.finished = finished;
    }

    // Getter
    public boolean getFinished() {
      return finished;
    }

    // toString method
    public String toString() {
      return name.toString();
    }
  }
}
