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
 * Classe que representa o custo da viagem
 * </p>
 */
public class Cost implements Weight {

    private double cost;

    /**
     * creates a new cost with specified data
     *
     * @param cost
     */
    public Cost(double cost) {
        if (cost > 0) {
            this.cost = cost;
        } else {
            throw new NumberFormatException();
        }

    }

    /**
     * creates a new cost empty
     */
    public Cost() {
    }

    /**
     * Defines a new weight
     *
     * @param weight
     */
    @Override
    public void setWeight(double weight) {
        this.cost = weight;
    }

    /**
     * Returns the weight in the double
     *
     * @return
     */
    @Override
    public double getWeight() {
        return cost;
    }

    /**
     * Returns true if the specified weight is instanceof of the current ones
     *
     * @param weight
     * @return
     */
    @Override
    public boolean checkWeight(Weight weight) {
        return (weight instanceof Cost);
    }

}
