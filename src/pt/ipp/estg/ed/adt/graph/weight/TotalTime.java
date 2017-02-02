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
 * Classe que representa o tempo total da viagem
 * </p>
 */
public class TotalTime implements Weight {

    private double time;

    /**
     * creates a new totaltime with specified data
     *
     * @param time
     */
    public TotalTime(double time) {
        if (time > 0) {
            this.time = time;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * creates a new totaltime empty
     */
    public TotalTime() {
    }

    /**
     * Defines a new weight
     *
     * @param weight
     */
    @Override
    public void setWeight(double weight) {
        this.time = weight;
    }

    /**
     * Returns the weight in the double
     *
     * @return
     */
    @Override
    public double getWeight() {
        return time;
    }

    /**
     * Returns true if the specified weight is instanceof of the current ones
     *
     * @param weight
     * @return
     */
    @Override
    public boolean checkWeight(Weight weight) {
        return (weight instanceof TotalTime);
    }

}
