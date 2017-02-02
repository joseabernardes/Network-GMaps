/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.queue;

import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.adt.stack.LinearNode;

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
public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int cont;

    public LinkedQueue() {
        rear = null;
        front = null;
        cont = 0;
    }

    @Override
    public void enqueue(T element) {
        LinearNode<T> temp = new LinearNode<T>(element, null);
        if (isEmpty()) {
            rear = temp;
            front = rear;
        } else {
            rear.setNext(temp);
            rear = rear.getNext();
        }
        cont++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        T ret;
        ret = front.getElem();
        front = front.getNext();
        cont--;
        return ret;

    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        return front.getElem();
    }

    @Override
    public boolean isEmpty() {
        return cont == 0;
    }

    @Override
    public int size() {
        return cont;
    }

    @Override
    public String toString() {

        String string = "Queue=[";

        if (isEmpty()) {
            string += "]";
            return string;
        }

        LinearNode<T> temp = front;
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
