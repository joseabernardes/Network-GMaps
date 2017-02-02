/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author José Bernardes
 */
public class TwoFieldsDialog extends Dialog {

    private final TextField fieldOne;
    private final TextField fieldTwo;
    private final Label labelOne;
    private final Label labelTwo;

    public TwoFieldsDialog() {
        super();
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        super.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        fieldOne = new TextField();
        fieldTwo = new TextField();
        labelOne = new Label();
        labelTwo = new Label();
        grid.add(labelOne, 0, 0);
        grid.add(fieldOne, 1, 0);
        grid.add(labelTwo, 0, 1);
        grid.add(fieldTwo, 1, 1);
        super.getDialogPane().setContent(grid);
    }

    public void setLabelOne(String label) {
        this.labelOne.setText(label);
    }

    public void setLabelTwo(String label) {
        this.labelTwo.setText(label);
    }

    public void setPromptTextFieldOne(String text) {
        this.fieldOne.setPromptText(text);

    }

    public void setPromptTextFieldTwo(String text) {
        this.fieldTwo.setPromptText(text);

    }

    public String getResults() {
        return fieldOne.getText() + "," + fieldTwo.getText();
    }

}
