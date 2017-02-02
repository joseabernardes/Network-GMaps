/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.graph.weight;

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
 * Classe que representa a distancia da viagem
 * </p>
 */
public class Distance implements Weight {

    private double distance;

    /**
     * creates a new distance with specifeid data
     *
     * @param distance
     */
    public Distance(double distance) {
        if (distance > 0) {
            this.distance = distance;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * creates a new distance empty
     */
    public Distance() {
    }

    /**
     * Defines a new weight
     *
     * @param weight
     */
    @Override
    public void setWeight(double weight) {
        this.distance = weight;
    }

    /**
     * Returns the weight in the double
     *
     * @return
     */
    @Override
    public double getWeight() {
        return distance;
    }

    /**
     * Returns true if the specified weight is instanceof of the current ones
     *
     * @param weight
     * @return
     */
    @Override
    public boolean checkWeight(Weight weight) {
        return (weight instanceof Distance);
    }

}
