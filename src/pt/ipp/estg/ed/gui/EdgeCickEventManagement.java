/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.gui;

import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import pt.ipp.estg.ed.adt.graph.NetworkADT;
import pt.ipp.estg.ed.adt.graph.weight.Distance;
import pt.ipp.estg.ed.adt.graph.weight.Time;
import pt.ipp.estg.ed.adt.graph.weight.Weight;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;

/**
 *
 * @author José Bernardes
 */
//versao mais dificil
//2. versão ainda mais dificil agora do master
public class EdgeCickEventManagement implements UIEventHandler {

    private final MapPoint store1, store2;
    private final NetworkADT<MapPoint> network;
    private final Stage mainStage;

    public EdgeCickEventManagement(MapPoint store1, MapPoint store2, NetworkADT<MapPoint> network, Stage mainStage) {
        this.store1 = store1;
        this.store2 = store2;
        this.network = network;
        this.mainStage = mainStage;
    }

    @Override
    public void handle(JSObject jso) {
        EdgeDialog dialog = new EdgeDialog();

        dialog.initOwner(mainStage);
        dialog.setTitle("Configurar Ligação");
        dialog.setHeaderText(store1.getTitle() + "<--> " + store2.getTitle());

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.APPLY) {
            try {
                if (dialog.removeOnePressed) {  // Se foi removido é porque não foi alterado!
                    network.removeEdge(store1, store2);
                } else if (dialog.isEditOnePressed()) { // se a primeira ligação foi alterada
                    Weight distance = new Distance(Double.parseDouble(dialog.getDistanceFieldOne()));
                    Weight time = new Time(Double.parseDouble(dialog.getTimeFieldOne()));

                    network.removeEdge(store1, store2);
                    network.addEdge(store1, store2, distance, time);
                    //commit
                }

                if (dialog.removeTwoPressed) { // Se foi removido é porque não foi alterado!
                    network.removeEdge(store2, store1);
                } else if (dialog.isEditTwoPressed()) { // se a segunda ligação foi alterada
                    Weight distance = new Distance(Double.parseDouble(dialog.getDistanceFieldTwo()));
                    Weight time = new Time(Double.parseDouble(dialog.getTimeFieldTwo()));
                    network.removeEdge(store2, store1);
                    network.addEdge(store2, store1, distance, time);
                }
            } catch (EmptyCollectionException ex) {
                Logger.getLogger(EdgeCickEventManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class EdgeDialog extends Dialog implements EventHandler<ActionEvent> {

        private final TextField distanceFieldOne, timeFieldOne;
        private final TextField distanceFieldTwo, timeFieldTwo;
        private final Label titleOne, titleTwo, distance, time;
        private final Button removeOne, removeTwo, editOne, editTwo;
        private final RadioButton aviaoOne, barcoOne, camiaoOne;
        private final RadioButton aviaoTwo, barcoTwo, camiaoTwo;
        private boolean editOnePressed, editTwoPressed, removeOnePressed, removeTwoPressed;

        public EdgeDialog() {
            super();
            editOnePressed = false;
            editTwoPressed = false;
            removeOnePressed = false;
            removeTwoPressed = false;
            super.getDialogPane().getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.APPLY);
            super.getDialogPane().lookupButton(ButtonType.APPLY).setDisable(true);
            removeOne = new Button("Remover Ligação");
            removeOne.setOnAction(this);
            removeTwo = new Button("Remover Ligação");
            removeTwo.setOnAction(this);
            editOne = new Button("Alterar");
            editOne.setOnAction(this);
            editTwo = new Button("Alterar");
            editTwo.setOnAction(this);
            titleOne = new Label("---->");
            titleOne.setFont(Font.font(null, FontWeight.NORMAL, 16));
            titleTwo = new Label("<----");
            titleTwo.setFont(Font.font(null, FontWeight.NORMAL, 16));
            distance = new Label("Distancia:(Km)");
            time = new Label("Tempo:(min)");
            aviaoOne = new RadioButton("Avião");
            barcoOne = new RadioButton("Barco");
            camiaoOne = new RadioButton("Camião");
            ToggleGroup radioOne = new ToggleGroup();
            radioOne.selectToggle(aviaoOne);
            radioOne.selectToggle(barcoOne);
            radioOne.selectToggle(camiaoOne);
            aviaoTwo = new RadioButton("Avião");
            barcoTwo = new RadioButton("Barco");
            camiaoTwo = new RadioButton("Camião");
            ToggleGroup radioTwo = new ToggleGroup();
            radioTwo.selectToggle(aviaoTwo);
            radioTwo.selectToggle(barcoTwo);
            radioTwo.selectToggle(camiaoTwo);
            double distanceOne = network.getWeights(store1, store2, new Distance());
            double timeOne = 0;
            if (distanceOne == 0) {
                editOne.setDisable(true);
                removeOne.setDisable(true);
            } else {
                timeOne = network.getWeights(store1, store2, new Time());
                
            }
            distanceFieldOne = new TextField(Double.toString(distanceOne));
            distanceFieldOne.setDisable(true);
            timeFieldOne = new TextField(Double.toString(timeOne));
            timeFieldOne.setDisable(true);

            double distanceTwo = network.getWeights(store2, store1, new Distance());
            double timeTwo = 0;
            if (distanceTwo == 0) {
                editTwo.setDisable(true);
                removeTwo.setDisable(true);
            } else {
                timeTwo = network.getWeights(store2, store1, new Time());

            }
            distanceFieldTwo = new TextField(Double.toString(distanceTwo));
            distanceFieldTwo.setDisable(true);
            timeFieldTwo = new TextField(Double.toString(timeTwo));
            timeFieldTwo.setDisable(true);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(titleOne, 1, 0);
            GridPane.setHalignment(titleOne, HPos.CENTER);
            grid.add(titleTwo, 2, 0);
            GridPane.setHalignment(titleTwo, HPos.CENTER);
            grid.add(distance, 0, 1);
            grid.add(distanceFieldOne, 1, 1);
            grid.add(distanceFieldTwo, 2, 1);
            grid.add(time, 0, 2);
            grid.add(timeFieldOne, 1, 2);
            grid.add(timeFieldTwo, 2, 2);

            grid.add(removeOne, 1, 3);
            grid.add(editOne, 1, 4);
            grid.add(removeTwo, 2, 3);
            grid.add(editTwo, 2, 4);
            super.getDialogPane().setContent(grid);

        }

        public boolean isEditOnePressed() {
            return editOnePressed;
        }

        public boolean isEditTwoPressed() {
            return editTwoPressed;
        }

        public boolean isRemoveOnePressed() {
            return removeOnePressed;
        }

        public boolean isRemoveTwoPressed() {
            return removeTwoPressed;
        }

        public String getDistanceFieldOne() {
            return distanceFieldOne.getText();
        }

        public String getTimeFieldOne() {
            return timeFieldOne.getText();
        }

        public String getDistanceFieldTwo() {
            return distanceFieldTwo.getText();
        }

        public String getTimeFieldTwo() {
            return timeFieldTwo.getText();
        }

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == editOne) {
                distanceFieldOne.setDisable(false);
                timeFieldOne.setDisable(false);
                editOnePressed = true;
                super.getDialogPane().lookupButton(ButtonType.APPLY).setDisable(false);

            } else if (event.getSource() == editTwo) {
                distanceFieldTwo.setDisable(false);
                timeFieldTwo.setDisable(false);
                editTwoPressed = true;
                super.getDialogPane().lookupButton(ButtonType.APPLY).setDisable(false);
            } else if (event.getSource() == removeOne) {

                removeOnePressed = true;
                editOne.setDisable(true);
                distanceFieldOne.setText("0");
                timeFieldOne.setText("0");
                super.getDialogPane().lookupButton(ButtonType.APPLY).setDisable(false);

            } else if (event.getSource() == removeTwo) {
                removeTwoPressed = true;
                editTwo.setDisable(true);
                distanceFieldTwo.setText("0");
                timeFieldTwo.setText("0");
                super.getDialogPane().lookupButton(ButtonType.APPLY).setDisable(false);

            }
        }
    }

}
