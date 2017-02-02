/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.stack;

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
 * NetworkADT defines the interface to a network.
 * </p>
 */
public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int size;

    public LinkedStack() {
        size = 0;
    }

    public LinkedStack(T element) {
        top = new LinearNode<T>(element, null);
        size = 1;
    }

    @Override
    public void push(T element) {
        LinearNode<T> temp = new LinearNode<T>(element, top);
        top = temp;
        size++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        T ret;
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        ret = top.getElem();
        top = top.getNext();
        size--;
        return ret;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        return top.getElem();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {

        String string = "stack=[";

        if (isEmpty()) {
            string += "]";
            return string;
        }

        LinearNode<T> temp = top;
        while (temp != null) {
            string += temp.getElem().toString();
            if (temp.getNext() != null) {
                string += ", ";
            } else {
                string += "]";
            }
            temp = temp.getNext();
        }
        return string;
    }

}
