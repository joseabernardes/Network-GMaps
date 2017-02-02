/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.unorderedList;

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
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds an element to the front of the list
     *
     * @param elem the element to be added to this list
     */
    public void addToFront(T elem);

    /**
     * Adds an element to the rear of the list
     *
     * @param elem the element to be added to this list
     */
    public void addToRear(T elem);

    /**
     * Adds an element after a particular element already in the list
     *
     * @param elem the element to be added to this list
     * @param target the previous element
     */
    public void addAfter(T elem, T target);

}
