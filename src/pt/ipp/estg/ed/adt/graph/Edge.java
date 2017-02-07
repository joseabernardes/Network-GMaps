/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.graph;

import pt.ipp.estg.ed.adt.graph.transport.Transport;
import pt.ipp.estg.ed.adt.graph.weight.TotalTime;
import pt.ipp.estg.ed.adt.graph.weight.Weight;
import pt.ipp.estg.ed.adt.graph.weight.Cost;

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
 * Class that represents the edge of an grah
 * </p>
 */
public class Edge<T> {

    private final Vertex<T> linkVertex;
    private final Weight distance;
    private final Weight time;
    private final Weight cost;
    private final Transport transport;

    /**
     * Creates a new edge with specified data
     *
     * @param linkVertex vertice that it is conected
     * @param distance distance between hte two vertices
     * @param time time that takes to go from one to the other
     * @param transport
     */
    public Edge(Vertex<T> linkVertex, Weight distance, Weight time, Transport transport) {
        this.linkVertex = linkVertex;
        this.distance = distance;
        this.time = time;
        this.transport = transport;
        this.cost = new Cost(Math.round((1.5d * distance.getWeight() + 5 * (time.getWeight() / 60)) * 100) / 100d);  //1.5€ por kilometro e 5€ por hora
        //   double cost = Math.round((1.5d*di + 5*(ti/60))*100)/100d;
    }

    /**
     * Method that returns the vertex that it is conected
     *
     * @return the vertex
     */
    public Vertex<T> getLinkVertex() {
        return linkVertex;
    }

    /**
     * Method that returns the weight in double
     *
     * @param weight rule that defines the weight that will be returned
     * @return the value of the weigh
     */
    public double getWeight(Weight weight) {

        if (weight.checkWeight(distance)) {     //distancia
            return distance.getWeight();
        } else if (weight.checkWeight(time)) {  //tempo em andamento
            return time.getWeight();
        } else if (weight.checkWeight(cost)) {  //custo total (parado e em andamento)
            return cost.getWeight(); //+ (linkVertex.getElement().getStopTime() / 60) * 2;  //2€ parado
        } else if (weight.checkWeight(new TotalTime())) { // tempo total (parado e em andamento)
            return time.getWeight(); //+ linkVertex.getElement().getStopTime();
        }
        return -1;
    }

    /**
     *
     * @return
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Method that return the textual representation of the vertice that it is conected
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return linkVertex.toString();
    }
//new Cost(Math.round(((1.5d * distance.getWeight() + 5 * (time.getWeight() / 60))) * 10) / 10f); 

    public static void main(String[] args) {

        double di = 10d;
        double ti = 23d;
        double q = 1.5d * di + 5 * (ti / 60);
        //15 + 
        System.out.println(q);
        q = Math.round(q * 100);
        System.out.println(q);
        q = q / 100;
        System.out.println(q);

        double cost = Math.round((1.5d * di + 5 * (ti / 60)) * 100) / 100d;// * 10) / 10f;  //1.5€ por kilometro e 5€ por hora
        System.out.println(cost);

    }
    // Math.round((((dWeigh / weight) * 60) * 10)) / 10f + " Km/h");  
}
