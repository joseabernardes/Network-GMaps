/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ipp.estg.ed.adt.graph.transport;

/**
 *
 * @author José Bernardes
 */
public class Barco implements Transport {

    private String name;

    public Barco() {
    }

    public Barco(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
