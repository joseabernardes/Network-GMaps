/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.graph;

import pt.ipp.estg.ed.adt.graph.weight.Weight;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ipp.estg.ed.adt.heap.HeapADT;
import pt.ipp.estg.ed.adt.heap.LinkedHeap;
import pt.ipp.estg.ed.adt.queue.LinkedQueue;
import pt.ipp.estg.ed.adt.queue.QueueADT;
import pt.ipp.estg.ed.adt.stack.LinkedStack;
import pt.ipp.estg.ed.adt.stack.StackADT;
import pt.ipp.estg.ed.adt.unorderedList.DoubleLinkedUnorderedList;
import pt.ipp.estg.ed.adt.unorderedList.UnorderedListADT;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;

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
public class LinkedNetwork<T> implements NetworkADT<T> {

    protected UnorderedListADT<Vertex<T>> vertices;

    /**
     * Creates a new empty network
     *
     *
     */
    public LinkedNetwork() {
        vertices = new DoubleLinkedUnorderedList<Vertex<T>>();

    }

    /**
     * Add a new vertice to the list of vertices
     *
     * @param vertex
     */
    @Override
    public void addVertex(T vertex) {
        vertices.addToRear(new Vertex<T>(vertex));
    }

    /**
     * Removes the vertice whose element matches with the specified and its edges
     *
     * @param vertex element of the vertice that we want to remove
     * @throws EmptyCollectionException
     */
    @Override
    public void removeVertex(T vertex) throws EmptyCollectionException {

        //remove as arestas a apontar para o VERTICE A ELIMINAR
        Vertex<T> temp = getVertex(vertex);
        Iterator<Vertex<T>> it = vertices.iterator();
        while (it.hasNext()) { // percorre todos os vertices
            Vertex<T> next = it.next();
            Iterator<Edge<T>> iterator = next.edgeIterator();
            while (iterator.hasNext()) { //percorre todas as arestas de um determinado vertice
                Edge<T> next1 = iterator.next();
                if (next1.getLinkVertex().getElement().equals(vertex)) { // verifica se um determinado vertice está ligado ao vertice a EMINIAR
                    next.removeEdge(next1);
                }

            }
        }
//        //remove o VERTICE A ELIMINAR e as arestas que partem dele
//        vertices.remove(temp);
//
//        Iterator<Edge<T>> iterator = temp.edgeIterator();
//        while (iterator.hasNext()) { // percorrer as arestas do vertice a eliminar
//            Vertex<T> temp2 = iterator.next().getLinkVertex(); // vertice ao qual aponta a aresta do VERTICE A ELIMINAR
//            Iterator<Edge<T>> iterator2 = temp2.edgeIterator();
//            while (iterator2.hasNext()) {//percorrer as arestas do vertice cuja aresta do VERTICE A ELIMINAR está ligado
//                Edge<T> next1 = iterator2.next();
//                if (next1.getLinkVertex().getElement().equals(vertex)) {
//                    temp2.removeEdge(next1);
//                }
//            }
//        }
//        //remove o VERTICE A ELIMINAR e as arestas que partem dele
//        vertices.remove(temp);

    }

    /**
     * Return the vertice whose element matches with the specified and null otherwise
     *
     * @param element
     * @return the vertex that contains the given element
     */
    private Vertex<T> getVertex(T element) {

        Iterator<Vertex<T>> iterator = vertices.iterator();
        while (iterator.hasNext()) {
            Vertex<T> temp = iterator.next();
            if (element.equals(temp.getElement())) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Inserts a new edge between to vertices
     *
     * @param element1 the source vertex
     * @param element2 the destiny vertex
     * @param distance the distance between the two vertex's
     * @param time the time between the two vertex's
     */
    @Override
    public void addEdge(T element1, T element2, Weight distance, Weight time) {
        Vertex<T> vertex1 = getVertex(element1);
        Vertex<T> vertex2 = getVertex(element2);
        vertex1.addEdge(vertex2, distance, time);
    }

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param element1 the source vertex
     * @param element2 the destiny vertex
     * @throws EmptyCollectionException if the graph is empty
     */
    @Override
    public void removeEdge(T element1, T element2) throws EmptyCollectionException {
        Vertex<T> vertex1 = getVertex(element1);
        Iterator<Edge<T>> iterator = vertex1.edgeIterator();
        Edge<T> next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (next.getLinkVertex().getElement().equals(element2)) {
                vertex1.removeEdge(next);
            }
        }

    }

    /**
     * Returns an iterator with all vertices that are connected to the specified
     *
     * @param element
     * @return
     */
    @Override
    public Iterator<T> iteratorAllEdges(T element) { //MAL FEITO, lista desnecessaria
        UnorderedListADT resultList = new DoubleLinkedUnorderedList<T>();
        Iterator<Edge<T>> edges = getVertex(element).edgeIterator();
        while (edges.hasNext()) {
            resultList.addToRear(edges.next().getLinkVertex().getElement());
        }
        return resultList.iterator();
    }

    /**
     * Returns a breadth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at the given vertex
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        Vertex<T> startIndex = getVertex(startVertex);

        Vertex<T> x;
        QueueADT<Vertex<T>> traversalQueue = new LinkedQueue<Vertex<T>>();
        UnorderedListADT<T> resultList = new DoubleLinkedUnorderedList<T>();

        if (startIndex == null) {
            return resultList.iterator();
        }

        for (Vertex<T> vertice : vertices) {
            vertice.setVisited(false);

        }

        traversalQueue.enqueue(startIndex);
        startIndex.setVisited(true);
        try {
            while (!traversalQueue.isEmpty()) {

                x = traversalQueue.dequeue();

                resultList.addToRear(x.getElement());
                // procura todos os vertices adjacentes a x que ainda nao foram visitados, e coloca-os na queue
                Iterator<Edge<T>> iterator = x.edgeIterator();

                while (iterator.hasNext()) {
                    Edge<T> next = iterator.next();
                    if (!next.getLinkVertex().isVisited()) {
                        traversalQueue.enqueue(next.getLinkVertex());
                        next.getLinkVertex().setVisited(true);
                    }
                }
            }

        } catch (EmptyCollectionException ex) {
            Logger.getLogger(LinkedNetwork.class.getName()).log(Level.SEVERE, null, ex);  //não chegará a ser lançada visto estar a ser lançada dentro do ciclo !isEmpty() 
        }
        return resultList.iterator();
    }

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the given vertex
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        Vertex<T> startIndex = getVertex(startVertex);

        Vertex<T> x;

        boolean found = false;
        StackADT<Vertex<T>> traversalStack = new LinkedStack<Vertex<T>>();
        UnorderedListADT<T> resultList = new DoubleLinkedUnorderedList<T>();

        if (startIndex == null) {
            return resultList.iterator();
        }

        for (Vertex<T> vertice : vertices) {
            vertice.setVisited(false);

        }

        traversalStack.push(startIndex);
        resultList.addToRear(startVertex);
        startIndex.setVisited(true);
        try {
            while (!traversalStack.isEmpty()) {

                x = traversalStack.peek();
                found = false;
                // procura todos os vertices adjacentes a x que ainda nao foram visitados, e coloca-os na stack
                Iterator<Edge<T>> iterator = x.edgeIterator();

                while (iterator.hasNext() && !found) {
                    Edge<T> next = iterator.next();
                    if (!next.getLinkVertex().isVisited()) {
                        traversalStack.push(next.getLinkVertex());
                        resultList.addToRear(next.getLinkVertex().getElement());
                        next.getLinkVertex().setVisited(true);
                        found = true;
                    }
                }
                if (!found && !traversalStack.isEmpty()) {
                    traversalStack.pop();
                }
            }

        } catch (EmptyCollectionException ex) {
            Logger.getLogger(LinkedNetwork.class.getName()).log(Level.SEVERE, null, ex);   //não chegará a ser lançada visto estar a ser lançada dentro do ciclo !isEmpty() 
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that makes the class Iterable thru
     *
     * @return the BFSIterator
     */
    @Override
    public Iterator<T> iterator() {
        try {
            return iteratorDFS(vertices.first().getElement());
        } catch (EmptyCollectionException ex) {
            //iterador vazio
            return (new DoubleLinkedUnorderedList<T>()).iterator();
        }
    }

    /**
     * Returns an iterator that contains the shortest path between the two vertices with the specified weight
     *
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @param rule the rule that determine the weight to be considered
     * @return an iterator that contains the shortest path between the two vertices
     */
    @Override
    public Iterator<T> iteratorShortestPath(T vertex1, T vertex2, Weight rule) {
        Vertex<T> startVertex = getVertex(vertex1);
        Vertex<T> targetVertex = getVertex(vertex2);

        Vertex<T> vertex = new Vertex<T>();
        double weight;

        HeapADT<Vertex<T>> minHeap = new LinkedHeap<Vertex<T>>();
        UnorderedListADT<T> resultList = new DoubleLinkedUnorderedList<T>();
        LinkedStack<Vertex<T>> stack = new LinkedStack<Vertex<T>>();

        for (Vertex<T> vertice : vertices) {
            vertice.setVisited(false);
            vertice.setWeight(Double.POSITIVE_INFINITY);

        }

        if (startVertex == null || targetVertex == null || startVertex.equals(targetVertex) || isEmpty()) {
            return resultList.iterator();
        }

        startVertex.setWeight(0);
        startVertex.setPredecessor(null);
        startVertex.setVisited(true);

        // atualizar o peso dos caminhos adjacentes ao startVertex até ao mesmo com exepcao deste(que é zero)
        for (Vertex<T> vertice : vertices) {
            if (!vertice.isVisited()) {
                Iterator<Edge<T>> it = startVertex.edgeIterator();
                while (it.hasNext()) {
                    Edge<T> next = it.next();
                    if (next.getLinkVertex().equals(vertice)) {
                        vertice.setWeight(startVertex.getWeight() + next.getWeight(rule));
                        vertice.setPredecessor(startVertex);
                        minHeap.addElement(vertice);
                    }
                }
            }
        }
        do {
            try {
                Vertex<T> min = minHeap.removeMin();
                weight = min.getWeight();
                minHeap = new LinkedHeap<Vertex<T>>();
                if (weight == Double.POSITIVE_INFINITY) { // se não tiver caminho
                    return resultList.iterator();
                } else {
                    vertex = min;
                    vertex.setVisited(true);
                }
            } catch (EmptyCollectionException ex) {
                Logger.getLogger(LinkedNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
            // atualizar o peso dos caminhos adjacentes ao ultimo vertice visitado até ao mesmo
            for (Vertex<T> vertice : vertices) {
                if (!vertice.isVisited()) {
                    Iterator<Edge<T>> itaa = vertex.edgeIterator();
                    Iterator<Edge<T>> it = vertice.edgeIterator();
                    while (itaa.hasNext()) {
                        Edge<T> next = itaa.next();
                        if (next.getLinkVertex().equals(vertice) && next.getWeight(rule) < Double.POSITIVE_INFINITY && vertex.getWeight() + next.getWeight(rule) < vertice.getWeight()) {
                            vertice.setWeight(vertex.getWeight() + next.getWeight(rule));
                            vertice.setPredecessor(vertex);
                        }
                    }
                    minHeap.addElement(vertice);
                }
            }
        } while (!minHeap.isEmpty() && !targetVertex.isVisited());

        vertex = targetVertex;
        stack.push(vertex);

        do {
            vertex = vertex.getPredecessor();
            stack.push(vertex);
        } while (vertex != startVertex);

        while (!stack.isEmpty()) {

            try {
                resultList.addToRear(stack.pop().getElement());
            } catch (EmptyCollectionException ex) {
                Logger.getLogger(LinkedNetwork.class.getName()).log(Level.SEVERE, null, ex);    //não chegará a ser lançada visto estar a ser lançada dentro do ciclo !isEmpty() 
            }
        }

        return resultList.iterator();
    }

    /**
     * returns an iterator with shortest path starting on vertex1 ending on vertex2 and passing through all the vertices of the list by the specified rule
     *
     * @param vertex1 the startvertex
     * @param vertex2 the endvertex
     * @param list the list of the passing vertexs
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the iterator
     */
    @Override
    public Iterator<T> iteratorShortestPathMultipleVertex(T vertex1, T vertex2, UnorderedListADT<T> list, Weight rule) {
        QueueADT<Double> queue;
        T last = vertex1;
        UnorderedListADT<T> resultList = new DoubleLinkedUnorderedList<T>();
        double sum = 0; //inutil!!
        while (!list.isEmpty()) {

            queue = new LinkedQueue<Double>();
            for (T t : list) {
//                t.setWeightWithStop(true); //apenas este vertice pode ser considerado o peso com o tempo de paragem
                Iterator<T> it = iteratorShortestPath(last, t, rule);
                queue.enqueue(shortestPathWeight(it, rule));        //a queue tem os elementos na mesma ordem da lista 
            }
            try {
                T lastVertex = list.first();
                double lastValue = queue.first();
                for (T t : list) {
                    double actualValue = queue.dequeue();
                    if (actualValue < lastValue) {
                        lastValue = actualValue;
                        lastVertex = t;
                    }
                }
                sum += lastValue;
                list.remove(lastVertex);
//                lastVertex.setWeightWithStop(true); //apenas este vertice pode ser considerado o peso com o tempo de paragem
                Iterator<T> iterator = iteratorShortestPath(last, lastVertex, rule);
                while (iterator.hasNext()) {
                    resultList.addToRear(iterator.next());
                }
                last = lastVertex;
                resultList.removeLast();

            } catch (EmptyCollectionException ex) {
                Logger.getLogger(LinkedNetwork.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

//        vertex2.setWeightWithStop(true); //apenas este certice pode ser considerado o peso com o tempo de paragem
        Iterator<T> iterator = iteratorShortestPath(last, vertex2, rule);
        while (iterator.hasNext()) {
            resultList.addToRear(iterator.next());
        }
        return resultList.iterator();
    }

    /**
     * Return the value of the specified weight between two vertices
     *
     * @param element the startVertex
     * @param element2 the endvertex
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the weight between the vertex's or -1 if there's no connection
     */
    @Override
    public double getWeights(T element, T element2, Weight rule) {
        Vertex<T> vert = getVertex(element);
        Iterator<Edge<T>> it = vert.edgeIterator();
        while (it.hasNext()) {
            Edge<T> edge = it.next();
            if (edge.getLinkVertex().getElement().equals(element2)) {
                return edge.getWeight(rule);
            }
        }
        return 0;//Se não existir ligação
    }

    /**
     * Returns the weight of the path that the given iterator represents
     *
     * @param patch the iterator
     * @param rule the rule that determine the weight to be considered by the algorithm
     * @return the weight
     */
    @Override
    public double shortestPathWeight(Iterator<T> patch, Weight rule) {

        double result = 0;
        Vertex<T> last;
        Vertex<T> vertex;

        if (patch.hasNext()) {
            last = getVertex(patch.next());
        } else {
            return Double.POSITIVE_INFINITY;
        }

        while (patch.hasNext()) {

            vertex = getVertex(patch.next());
            Iterator<Edge<T>> iterator = last.edgeIterator();

            while (iterator.hasNext()) {
                Edge<T> next = iterator.next();
                if (next.getLinkVertex().equals(vertex)) {
                    result += next.getWeight(rule);
//                    next.getLinkVertex().getElement().setWeightWithStop(false);

                }
            }
            last = vertex;
        }
        return result;
    }

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty
     */
    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected
     */
    @Override
    public boolean isConnected() {
        try {
            if (!isEmpty()) {
                int contador = 0;

                Iterator<T> iterator = iteratorDFS(vertices.first().getElement());

                while (iterator.hasNext()) {
                    contador++;
                }
                return contador == size();

            }
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(LinkedNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph
     */
    @Override
    public int size() {
        return vertices.size();
    }

    /**
     * Returns a textual representation of the vertices.
     *
     * @return a string representation of the adjacency matrix
     */
    @Override
    public String toString() {
        return vertices.toString();
    }
}
