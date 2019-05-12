package view.tabs.tab4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import presenter.ImagesService;
import sample.Main;
import view.Utils;

import javax.swing.*;
import java.io.IOException;

public class Tab4Controller extends Tab {

    private int maxMaskSize = 31;


    private ImagesService imagesService;

    @FXML public TextField borderHighlightMaskSize;
    @FXML public TextField logSD;
    @FXML public TextField cannyT1;
    @FXML public TextField cannyT2;
    @FXML public TextField susanT;
    @FXML public TextField houghLinesEps;
    @FXML public TextField houghLinesAmountOfLines;

    public Tab4Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_4.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("Error loading tab_4.fxml", e);
        }
        imagesService = Main.getImagesService();
    }

    @FXML
    protected void borderHighlight(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(borderHighlightMaskSize.getText(), maxMaskSize);
        if(value == -1) {
            return;
        }
        imagesService.borderHighlight(value);
    }
    @FXML
    protected void prewittOperatorOpen3(ActionEvent event) {
        imagesService.prewittOperatorOpen3();
    }

    @FXML
    protected void sobelOperatorOpen3(ActionEvent event) {
        imagesService.sobelOperatorOpen3();
    }

    @FXML
    protected void kirshOperatorOpen3(ActionEvent event) {
        imagesService.kirshOperatorOpen3();
    }

    @FXML
    protected void unnamedOperatorOpen3(ActionEvent event) {
        imagesService.unnamedOperatorOpen3();
    }

    @FXML
    protected void laplaceCrossingZeroOperator(ActionEvent event){
        imagesService.laplaceCrossingZeroOperator();
    }

    @FXML
    protected void laplaceOperator(ActionEvent event){
        imagesService.laplaceOperator();
    }

    @FXML
    protected void canny(ActionEvent event) {
        int t1 = Utils.sanitizeNumberInput(cannyT1.getText(), Utils.L-1);
        int t2 = Utils.sanitizeNumberInput(cannyT2.getText(), Utils.L-1);

        if(t1 == -1 || t2 == -1) {
            return;
        }
        imagesService.cannyOperator(t1, t2);
    }

    @FXML
    protected void borderSusan(ActionEvent event) {
        int t = Utils.sanitizeNumberInput(susanT.getText(), Utils.L-1);

        if(t == -1){
            return;
        }
        imagesService.borderSusanOperator(t);
    }


    @FXML
    protected void cornerSusan(ActionEvent event) {
        int t = Utils.sanitizeNumberInput(susanT.getText(), Utils.L-1);

        if(t == -1){
            return;
        }
        imagesService.cornerSusanOperator(t);
    }

    @FXML
    protected void houghCircles(ActionEvent event) {
        double eps = Utils.dsanitizeNumberInput(houghLinesEps.getText(), Utils.L-1);
        int amountOfLines = Utils.sanitizeNumberInput(houghLinesAmountOfLines.getText(), Utils.L-1);

        if(eps == -1 || amountOfLines == -1){
            return;
        }
        imagesService.houghCirlces(eps, amountOfLines);

    }

    @FXML
    protected void houghLines(ActionEvent event) {
        double eps = Utils.dsanitizeNumberInput(houghLinesEps.getText(), Utils.L-1);
        int amountOfLines = Utils.sanitizeNumberInput(houghLinesAmountOfLines.getText(), Utils.L-1);

        if(eps == -1 || amountOfLines == -1){
            return;
        }
        imagesService.houghLines(eps, amountOfLines);

    }

    @FXML
    protected  void logOperator(ActionEvent event) {
        double value = Utils.dsanitizeNumberInput(logSD.getText(), Utils.L-1.0);

        if(value == -1) {
            return;
        }
        imagesService.logOperator(value);

    }
}
