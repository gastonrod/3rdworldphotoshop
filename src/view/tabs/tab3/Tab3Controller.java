package view.tabs.tab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import presenter.ImagesService;
import sample.Main;
import view.Utils;

import java.io.IOException;

public class Tab3Controller extends Tab {

    private ImagesService imagesService;


    @FXML
    public TextField gaussianNoiseMeanTF;
    @FXML
    public TextField gaussianNoiseStdTF;
    @FXML
    public TextField gaussianNoisePercentTF;

    @FXML
    public TextField saltAndPepperNoisePercentTF;

    @FXML
    public TextField rayleighNoiseMean;
    @FXML
    public TextField rayleighNoisePercentTF;

    @FXML
    public TextField exponentialNoiseLambda;
    @FXML
    public TextField exponentialNoisePercentTF;

    public Tab3Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_3.fxml"));
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
    protected void gaussianNoise(ActionEvent event) {
        int mean    = Utils.sanitizeNumberInput(gaussianNoiseMeanTF.getText(), Utils.L -1);
        int std     = Utils.sanitizeNumberInput(gaussianNoiseStdTF.getText(), Utils.L - 1);
        double percent = Utils.sanitizeNumberInput(gaussianNoisePercentTF.getText(), 1.0);
        if(mean == -1 || std == -1 || percent == -1) {
           return;
        }
        imagesService.gaussianNoise(mean, std, percent);
    }

    @FXML
    protected void exponentialNoise(ActionEvent event) {
        double lambda = Utils.sanitizeNumberInput(exponentialNoiseLambda.getText(), Utils.L - 1.0);
        double percent = Utils.sanitizeNumberInput(exponentialNoisePercentTF.getText(), 1.0);
        if(lambda == -1 || percent == -1) {
            return;
        }
        imagesService.exponentialNoise(lambda, percent);
    }

    @FXML
    protected void rayleighNoise(ActionEvent event) {
        double mean = Utils.sanitizeNumberInput(rayleighNoiseMean.getText(), Utils.L -1.0);
        double percent = Utils.sanitizeNumberInput(rayleighNoisePercentTF.getText(), 1.0);
        if(mean == -1 || percent == -1) {
            return;
        }
        imagesService.rayleighNoise(mean, percent);
    }

    @FXML
    protected void saltAndPepperNoise(ActionEvent event) {
        double percent = Utils.sanitizeNumberInput(saltAndPepperNoisePercentTF.getText(), 1.0);
        if(percent == -1) {
            return;
        }
        imagesService.saltAndPepper(percent);
    }
}
