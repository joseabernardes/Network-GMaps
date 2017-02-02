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
public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T elem) {
        DoubleNode<T> newN = new DoubleNode<T>(elem, head, head.getNext());
        head.setNext(newN);
        newN.getNext().setPrevious(newN);
        size++;
    }

    @Override
    public void addToRear(T elem) {
        DoubleNode<T> newN = new DoubleNode<T>(elem, tail.getPrevious(), tail);
        tail.setPrevious(newN);
        newN.getPrevious().setNext(newN);
        size++;
    }

    @Override
    public void addAfter(T elem, T target) {

        boolean found = false;
        DoubleNode<T> temp = head;

        while (!found && temp.getNext().getElem() != null) {
            temp = temp.getNext();
            if (temp.getElem().equals(target)) {
                DoubleNode<T> newN = new DoubleNode<T>(elem, temp, temp.getNext());
                temp.setNext(newN);
                newN.getNext().setPrevious(newN);
                size++;
                found = true;

            }

        }
    }


}
