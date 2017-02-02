/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.queue;

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
public interface QueueADT<T> {
    
    /**
 * Adds one element to the rear of this queue.
 *
 * @param element the element to be added to
 * the rear of this queue
 */
 public void enqueue(T element);
 /**
 * Removes and returns the element at the front of
 * this queue.
 *
 * @return the element at the front of this queue
     * @throws pt.ipp.estg.ed.exceptions.EmptyCollectionException
 */
 public T dequeue() throws EmptyCollectionException;
 /**
 * Returns without removing the element at the front of
 * this queue.
 *
 * @return the first element in this queue
     * @throws pt.ipp.estg.ed.exceptions.EmptyCollectionException
 */
 public T first() throws EmptyCollectionException ;

/**
 * Returns true if this queue contains no elements.
 *
 * @return true if this queue is empty
 */
 public boolean isEmpty();
 /**
 * Returns the number of elements in this queue.
 *
 * @return the integer representation of the size
 * of this queue
 */
 public int size();
 /**
 * Returns a string representation of this queue.
 *
 * @return the string representation of this queue
 */
 @Override
 public String toString();
}
