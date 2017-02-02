/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.adt.binaryTree;

import pt.ipp.estg.ed.exceptions.ElementNotFoundException;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ipp.estg.ed.adt.queue.LinkedQueue;
import pt.ipp.estg.ed.adt.queue.QueueADT;
import pt.ipp.estg.ed.adt.unorderedList.DoubleLinkedUnorderedList;
import pt.ipp.estg.ed.adt.unorderedList.UnorderedListADT;

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
 * LinkedBinaryTree implements the BinaryTreeADT interface
 * </p>
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);

    }

    /**
     * Returns a reference to the root element
     *
     * @return a reference to the root
     */
    @Override
    public T getRoot() {

        return root.getElement();
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the integer number of elements in this tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns true if the binary tree contains an element that matches the specified element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    @Override
    public boolean contains(T targetElement) {
        try {
            find(targetElement);
            return true;
        } catch (ElementNotFoundException ex) {
            return false;
        }
    }

    /**
     * Returns a reference to the specified target element if it is found in this binary tree. Throws a NoSuchElementException if the specified target element
     * is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.getElement());
    }

    /**
     * Returns a reference to the specified target element if it is found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.getElement().equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.getLeft());

        if (temp == null) {
            temp = findAgain(targetElement, next.getRight());
        }

        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> tempList = new DoubleLinkedUnorderedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder(BinaryTreeNode<T> node, UnorderedListADT<T> tempList) {
        if (node != null) {
            inorder(node.getLeft(), tempList);
            tempList.addToRear(node.getElement());
            inorder(node.getRight(), tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> tempList = new DoubleLinkedUnorderedList<T>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    protected void preOrder(BinaryTreeNode<T> node, UnorderedListADT<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.getElement());
            preOrder(node.getLeft(), tempList);
            preOrder(node.getRight(), tempList);

        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> tempList = new DoubleLinkedUnorderedList<T>();
        postOrder(root, tempList);

        return tempList.iterator();
    }

    protected void postOrder(BinaryTreeNode<T> node, UnorderedListADT<T> tempList) {
        if (node != null) {
            postOrder(node.getLeft(), tempList);
            postOrder(node.getRight(), tempList);
            tempList.addToRear(node.getElement());
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        QueueADT<BinaryTreeNode<T>> nodes = new LinkedQueue<BinaryTreeNode<T>>();
        UnorderedListADT<T> results = new DoubleLinkedUnorderedList<T>();
        if (root != null) {
            nodes.enqueue(root);
            while (!nodes.isEmpty()) {
                try {
                    BinaryTreeNode<T> temp = nodes.dequeue();
                    results.addToRear(temp.getElement());
                    /**
                     * enqueue the children'S of the element
                     */
                    if (temp.getLeft() != null) {
                        nodes.enqueue(temp.getLeft());
                    }
                    if (temp.getRight() != null) {
                        nodes.enqueue(temp.getRight());
                    }

                } catch (EmptyCollectionException ex) {
                    Logger.getLogger(LinkedBinaryTree.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        return results.iterator();
    }
}
