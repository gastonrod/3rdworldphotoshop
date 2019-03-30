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

    private int gaussianDefaultMean = 0;
    private int gaussianNoiseDefaultStd = 80;
    private int gaussianFilterDefaultStd = 80;

    private double rayleighDefaultMean = 0.5;

    private double exponentialDefaultLambda = 0.5;

    private double noiseDefaultPercent = 0.3;

    private ImagesService imagesService;

    @FXML
    public TextField gaussianFilterSD;

    @FXML public TextField borderHighlightMaskSize;

    @FXML
    public TextField borderDetectionYTF;

    @FXML
    public TextField borderDetectionXTF;

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
    protected void borderHighlight(ActionEvent event) {
        int value = Utils.sanitizeNumberInput(borderHighlightMaskSize.getText(), maxMaskSize);
        if(value == -1) {
            return;
        }
        imagesService.borderHighlight(value);
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
    protected void gaussianFilter(ActionEvent event) {
        double sd = Utils.sanitizeNumberInput(gaussianFilterSD.getText(), gaussianFilterDefaultStd);
        imagesService.gaussianFilter(sd);
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
        int std     = Utils.sanitizeNumberInput(gaussianNoiseStdTF.getText(), Utils.L - 1, gaussianNoiseDefaultStd);
        double percent = Utils.sanitizeNumberInput(gaussianNoisePercentTF.getText(), 1.0, noiseDefaultPercent);
        if(mean == -1 || std == -1 || percent == -1) {
           return;
        }
        imagesService.gaussianNoise(mean, std, percent);
    }

    @FXML
    protected void exponentialNoise(ActionEvent event) {
        double lambda = Utils.sanitizeNumberInput(exponentialNoiseLambda.getText(), Utils.L - 1.0, exponentialDefaultLambda);
        double percent = Utils.sanitizeNumberInput(exponentialNoisePercentTF.getText(), 1.0, noiseDefaultPercent);
        if(lambda == -1 || percent == -1) {
            return;
        }
        imagesService.exponentialNoise(lambda, percent);
    }

    @FXML
    protected void rayleighNoise(ActionEvent event) {
        double mean = Utils.sanitizeNumberInput(rayleighNoiseMean.getText(), Utils.L -1.0, rayleighDefaultMean);
        double percent = Utils.sanitizeNumberInput(rayleighNoisePercentTF.getText(), 1.0, noiseDefaultPercent);
        if(mean == -1 || percent == -1) {
            return;
        }
        imagesService.rayleighNoise(mean, percent);
    }

    @FXML
    protected void saltAndPepperNoise(ActionEvent event) {
        double percent = Utils.sanitizeNumberInput(saltAndPepperNoisePercentTF.getText(), 1.0, noiseDefaultPercent);
        if(percent == -1) {
            return;
        }
        imagesService.saltAndPepper(percent);
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
}
