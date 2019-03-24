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

    private int maxMaskSize = 32;
    private int gaussianDefaultMean    = 80;
    private int gaussianDefaultStd     = 0;
    private double gaussianDefaultPercent = 0.3;
    private ImagesService imagesService;

    @FXML
    public TextField gaussianNoiseMeanTF;
    @FXML
    public TextField gaussianNoiseStdTF;
    @FXML
    public TextField gaussianNoisePercentTF;
    @FXML
    public TextField averageTextField;

    @FXML
    public TextField medianTextField;

    @FXML
    public TextField weightedMedianWeightTextField;

    @FXML
    public TextField weightedMedianTextField;

    public Tab3Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_3.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("Error loading tab_3.fxml", e);
        }
        imagesService = Main.getImagesService();
    }

    @FXML
    protected void medianFilter(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(medianTextField.getText(), maxMaskSize);
        if(value == -1) {
            return;
        }
        imagesService.medianFilter(value);
    }

    @FXML
    protected void weightedMedianFilter(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(weightedMedianTextField.getText(), maxMaskSize);
        int weight = Utils.sanitizeNumberInput(weightedMedianWeightTextField.getText(), maxMaskSize);
        if(value == -1) {
            return;
        }
        imagesService.weightedMedianFilter(value, weight);
    }

    @FXML
    protected void averageFilter(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(averageTextField.getText(), maxMaskSize);
        if(value == -1) {
            return;
        }
        imagesService.averageFilter(value);
    }

    @FXML
    protected void gaussianNoise(ActionEvent event) {
        int mean    = Utils.sanitizeNumberInput(gaussianNoiseMeanTF.getText(), Utils.L -1, gaussianDefaultMean);
        int std     = Utils.sanitizeNumberInput(gaussianNoiseStdTF.getText(), Utils.L - 1, gaussianDefaultStd);
        double percent = Utils.sanitizeNumberInput(gaussianNoisePercentTF.getText(), 1.0,  gaussianDefaultPercent);
        if(mean == -1 || std == -1 || percent == -1) {
           return;
        }
        imagesService.gaussianNoise(mean, std, percent);
    }
}
