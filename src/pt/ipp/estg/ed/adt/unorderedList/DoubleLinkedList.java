/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.unorderedList;

import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import java.util.Iterator;

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
public class DoubleLinkedList<T> implements ListADT<T> {

    protected DoubleNode<T> head;
    protected DoubleNode<T> tail;
    protected int size;

    public DoubleLinkedList() {
        head = new DoubleNode<T>();
        tail = new DoubleNode<T>();
        head.setNext(tail); ////link the head to the tail
        tail.setPrevious(head);//link the tail to the head
        size = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        T elem = head.getNext().getElem();
        head.setNext(head.getNext().getNext());
        head.getNext().setPrevious(head);
        size--;

        return elem;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        T elem = tail.getPrevious().getElem();
        tail.setPrevious(tail.getPrevious().getPrevious());
        tail.getPrevious().setNext(tail);
        size--;
        return elem;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        DoubleNode<T> temp = head;

        while (temp.getNext().getElem() != null) {
            temp = temp.getNext();
            if (temp.getElem().equals(element)) {
                T elem = temp.getElem();
                temp.getPrevious().setNext(temp.getNext());
                temp.getNext().setPrevious(temp.getPrevious());
                size--;
                return elem;
            }
        }
        return null;

    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        return head.getNext().getElem();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        return tail.getPrevious().getElem();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Vazio");
        }
        DoubleNode<T> temp = head;
        while (temp.getNext().getElem() != null) {
            temp = temp.getNext();
            if (temp.getElem().equals(target)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private class BasicIterator<T> implements Iterator<T> {

        DoubleNode<T> iterator;

        public BasicIterator() {
            iterator = (DoubleNode<T>) head;
        }

        @Override
        public boolean hasNext() {

            return iterator.getNext().getElem() != null;
        }

        @Override
        public T next() {
            iterator = iterator.getNext();

            return iterator.getElem();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    @Override
    public String toString() {

        String string = "list=[";

        if (isEmpty()) {
            string += "]";
            return string;
        }

        DoubleNode<T> temp = head;

        while (temp.getNext().getElem() != null) {
            temp = temp.getNext();
            string += temp.getElem().toString();
            if (temp.getNext().getNext() != null) {
                string += ", ";
            } else {
                string += ", " + size() + "]";
            }
        }
        return string;
    }
}
