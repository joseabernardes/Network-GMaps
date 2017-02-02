/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.map.mapPoint;

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
 * Interface que representa um ponto do mapa
 * </p>
 */
public interface MapPoint {

    /**
     * returns the name of the map point
     *
     * @return
     *
     */
    public String getTitle();

    /**
     * sets a new name
     *
     * @param title
     *
     */
    public void setTitle(String title);

    /**
     * returns the latitude of the map point
     *
     * @return
     *
     */
    public double getLatitude();

    /**
     * sets a new latitude
     *
     * @param latitude
     */
    public void setLatitude(double latitude);

    /**
     * returns the longitude of the map point
     *
     * @return
     *
     */
    public double getLongitude();

    /**
     * sets a new longitude
     *
     * @param longitude
     */
    public void setLongitude(double longitude);

    /**
     * returns the stop time in a certain map point
     *
     * @return
     *
     */
//    public double getStopTime();

    /**
     * sets a new stop time
     *
     * @param stopTime
     *
     */
//    public void setStopTime(double stopTime);

    /**
     * flag that represents the possibility of a existance of the stop time
     *
     * @return true if that exists or false otherwise
     *
     */
    public boolean weightWithStop();

    /**
     * sets a new weightWithStop
     *
     * @param weightWithStop
     *
     */
    public void setWeightWithStop(boolean weightWithStop);

    /**
     * returns the id of the ciy
     *
     * @return
     */
    public int getId();

}
