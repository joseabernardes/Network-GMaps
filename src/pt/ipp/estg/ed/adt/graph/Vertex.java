/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.graph;

import java.util.Iterator;
import pt.ipp.estg.ed.adt.graph.transport.Camiao;
import pt.ipp.estg.ed.adt.graph.weight.Weight;
import pt.ipp.estg.ed.adt.unorderedList.DoubleLinkedUnorderedList;
import pt.ipp.estg.ed.adt.unorderedList.UnorderedListADT;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;


/**
 * <h3>
 * ESTG - Escola Superior de Tecnologia e Gestão<br>
 * ED - Trabalho Pratico<br>
 * </h3>
 * <p>
 * <strong>Nome:</strong> Joel Ribeiro Pereira<br>
 * <strong>Número:</strong> 8150138<br>
 * <strong>Turma:</strong> LEI2T3<br>
 * <p>
 * <strong>Nome:</strong> José Paulo de Almeida Bernardes<br>
 * <strong>Número:</strong> 8150148<br>
 * <strong>Turma:</strong> LEI2T3<br>
 * </p>
 * <p>
 * <strong>Descrição: </strong><br>
 * Interface that represents the graph data structure.
 * </p>
 */
public class Vertex<T> implements Comparable<Vertex<T>> {

    private T element;
    private UnorderedListADT<Edge<T>> adjList;
    //for algorithm
    private boolean visited;
    private double weight;
    private Vertex<T> predecessor;

    /**
     * creates a new vertice with specified data
     *
     * @param element element of the vertex
     */
    public Vertex(T element) {
        this.element = element;
        adjList = new DoubleLinkedUnorderedList<Edge<T>>();
        visited = false;
        weight = Double.POSITIVE_INFINITY;
        predecessor = null;
    }

    /**
     * Creates a new empty vertex
     */
    public Vertex() {
    }

    /**
     * Returns the element of the vertez
     *
     * @return
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets a new element in the vertex
     *
     * @param element
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * creates a new edge between two vertices
     *
     * @param linkVertex vertice that we pretend two connect
     * @param distance distance between the two edges
     * @param time time that takes to go from one to the other
     */
    public void addEdge(Vertex<T> linkVertex, Weight distance, Weight time) {
        adjList.addToRear(new Edge(linkVertex, distance, time, new Camiao("Camião")));
    }

    /**
     * returns an iterator with all vertices that it is connected
     *
     * @return
     */
    public Iterator<Edge<T>> edgeIterator() {
        return adjList.iterator();
    }

    /**
     * remove the edge between two vertices
     *
     * @param edge
     * @throws EmptyCollectionException
     */
    public void removeEdge(Edge<T> edge) throws EmptyCollectionException {
        adjList.remove(edge);

    }

    /**
     * return the textual represention of the element of the vertice
     *
     * @return
     */
    @Override
    public String toString() {
        return element.toString();
    }

    /**
     * needed for the algorithm returns true if tha vertice was visited anda false it wasnt
     *
     * @return
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * needed for the algorithm sets a new true if the vertice was visited or false otherwise
     *
     * @param visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * returns in double the weight
     *
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * sets a new weight
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * returns the predecessor vertice
     *
     * @return
     */
    public Vertex<T> getPredecessor() {
        return predecessor;
    }

    /**
     * sets a new predecessor vertice
     *
     * @param predecessor
     */
    public void setPredecessor(Vertex<T> predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * compares two vertices through the weight
     *
     * @param o the vertice to compare
     * @return
     */
    @Override
    public int compareTo(Vertex<T> o) {
        if (this.weight > o.getWeight()) {
            return 1;
        } else if (this.weight == o.getWeight()) {
            return 0;
        } else {
            return -1;
        }
    }

}
