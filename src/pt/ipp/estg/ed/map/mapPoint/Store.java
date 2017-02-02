/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.map.mapPoint;

import pt.ipp.estg.ed.exceptions.InvalidIdException;

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
 * Classe que reprsenta uma loja
 * </p>
 */
public class Store implements MapPoint {

    public static int ID = 1;
    private final int id;
    private String title;
    private double latitude;
    private double longitude;
//    private Weight stopTime;
    private boolean weightWithStop;

    /**
     * creates a new Store with specifeid data
     *
     * @param title
     * @param id
     * @param latitude
     * @param longitude
     */
    public Store(String title, int id, double latitude, double longitude) {
        this.weightWithStop = false;
        Store.ID++;
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;

//        this.stopTime = new Time(0);
    }

    /**
     *
     * @param id
     * @throws InvalidIdException
     */
    public Store(int id) throws InvalidIdException {
        this.weightWithStop = true;
        if (id > Store.ID + 1 || id <= 0) {
            throw new InvalidIdException();
        } else {
            this.id = id;
            this.title = null;

        }
    }

    /**
     * returns the id of the Store
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * returns the name of the Store
     *
     * @return
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * sets a new name
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the textual representation of th Store
     *
     * @return
     */
    @Override
    public String toString() {
        return title ;
    }

    /**
     * returns the stop time in the Store
     *
     * @return
     */
//    @Override
//    public double getStopTime() {
//        if (weightWithStop()) {
//            return stopTime.getWeight();
//        } else {
//            return 0.0;
//        }
//    }
    /**
     * sets a new stop time
     *
     * @param stopTime
     */
//    @Override
//    public void setStopTime(double stopTime) {
//        this.stopTime.setWeight(stopTime);
//    }
    /**
     * flag that represents the possibility of a existance of the stop time
     *
     * @return true if that exists or false otherwise
     *
     */
    @Override
    public boolean weightWithStop() {
        return weightWithStop;
    }

    /**
     * sets a new weightWithStop
     *
     * @param weightWithStop
     */
    @Override
    public void setWeightWithStop(boolean weightWithStop) {
        this.weightWithStop = weightWithStop;
    }

    /**
     * compare two Store's through the id
     *
     * @param obj
     * @return true if they are equals or false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Store other = (Store) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static int getID() {
        return Store.ID;
    }

}
