/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.exceptions;

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
 * CLasse que representa uma excepção de elemento não encontrado
 * </p>
 */
public class ElementNotFoundException extends Exception {

    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }

}
