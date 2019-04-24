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
    @FXML public TextField laplaceThreshold;
    @FXML public TextField logSD;

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
    protected void prewittOperatorY(ActionEvent event) {
        imagesService.prewittOperatorY();
    }

    @FXML
    protected void prewittOperatorX(ActionEvent event) {
        imagesService.prewittOperatorX();
    }

    @FXML
    protected void prewittOperatorBoth(ActionEvent event) {
        imagesService.prewittOperatorBoth();
    }

    @FXML
    protected void prewittOperatorOpen3(ActionEvent event) {
        imagesService.prewittOperatorOpen3();
    }

    @FXML
    protected void sobelOperatorBoth(ActionEvent event) {
        imagesService.sobelOperatorBoth();
    }

    @FXML
    protected void sobelOperatorOpen3(ActionEvent event) {
        imagesService.sobelOperatorOpen3();
    }

    @FXML
    protected void laplaceCrossingZeroOperator(ActionEvent event){
        int value = Utils.sanitizeNumberInput(laplaceThreshold.getText(), Utils.L-1);

        if(value == -1) {
            return;
        }
        imagesService.laplaceCrossingZeroOperator(value);
    }

    @FXML
    protected void laplaceOperator(ActionEvent event){
        int value = Utils.sanitizeNumberInput(laplaceThreshold.getText(), Utils.L-1);

        if(value == -1) {
            return;
        }
        imagesService.laplaceOperator(value);
    }

    @FXML
    protected  void logOperator(ActionEvent event) {
        double value = Utils.dsanitizeNumberInput(logSD.getText(), Utils.L-1);

        if(value == -1) {
            return;
        }
        imagesService.logOperator(value);

    }
}
