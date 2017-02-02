/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.graph;

import pt.ipp.estg.ed.adt.graph.weight.Weight;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;
import java.util.Iterator;
import pt.ipp.estg.ed.adt.unorderedList.UnorderedListADT;

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
 * NetworkADT defines the interface to a network.
 * </p>
 */
public interface NetworkADT<T> extends GraphADT<T>, Iterable<T> {

    /**
     * Inserts a new edge between to vertices
     *
     * @param element1 the source vertex
     * @param element2 the destiny vertex
     * @param distance the distance between the two vertex's
     * @param time the time between the two vertex's
     */
    public void addEdge(T element1, T element2, Weight distance, Weight time);

    /**
     * Returns an iterator that contains the shortest path between the two vertices with the specified weight
     *
     * @param startVertex the starting vertex
     * @param targetVertex the ending vertex
     * @param rule the rule that determine the weight to be considered
     * @return an iterator that contains the shortest path between the two vertices
     */
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex, Weight rule);

   /**
     * Returns the weight of the path that the given iterator represents
     *
     * @param patch the iterator
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the weight
     */
    public double shortestPathWeight(Iterator<T> patch, Weight rule);

    /**
     * returns an iterator with shortest path starting on vertex1 ending on vertex2 and passing through all the vertices of the list by the specified rule
     *
     * @param vertex1 the startvertex
     * @param vertex2 the endvertex
     * @param list the list of the passing vertexs
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the iterator
     */
    public Iterator<T> iteratorShortestPathMultipleVertex(T vertex1, T vertex2, UnorderedListADT<T> list, Weight rule);

    /**
     * Return the value of the specified weight between two vertices
     *
     * @param element the startVertex
     * @param element2 the endvertex
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the weight between the vertex's
     */
    public double getWeights(T element, T element2, Weight rule);
}
