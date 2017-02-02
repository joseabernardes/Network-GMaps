/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.gui;

import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import pt.ipp.estg.ed.adt.graph.NetworkADT;
import pt.ipp.estg.ed.adt.graph.weight.Cost;
import pt.ipp.estg.ed.adt.graph.weight.Distance;
import pt.ipp.estg.ed.adt.graph.weight.Time;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;

/**
 *
 * @author José Bernardes
 */
public class EdgeCickEvent implements UIEventHandler {

    private final MapPoint store1, store2;
    private final NetworkADT<MapPoint> network;
    private final Stage mainStage;

    public EdgeCickEvent(MapPoint store1, MapPoint store2, NetworkADT<MapPoint> network, Stage mainStage) {
        this.store1 = store1;
        this.store2 = store2;
        this.network = network;
        this.mainStage = mainStage;
    }

    @Override
    public void handle(JSObject jso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainStage);
        alert.setTitle(store1.getTitle() + " <-> " + store2.getTitle());
        alert.setHeaderText(null);
        String context = store1.getTitle() + " -> " + store2.getTitle();
        context += "\nDistancia: " + network.getWeights(store1, store2, new Distance()) + " Km";
        context += "\nTempo: " + network.getWeights(store1, store2, new Time()) + " min";
        context += "\nCusto: " + network.getWeights(store1, store2, new Cost()) + " €";
        context += "\n\n" + store2.getTitle() + " -> " + store1.getTitle();
        context += "\nDistancia: " + network.getWeights(store2, store1, new Distance()) + " Km";
        context += "\nTempo: " + network.getWeights(store2, store1, new Time()) + " min";
        context += "\nCusto: " + network.getWeights(store2, store1, new Cost()) + " €";

        alert.setContentText(context);
        alert.showAndWait();
    }
}
