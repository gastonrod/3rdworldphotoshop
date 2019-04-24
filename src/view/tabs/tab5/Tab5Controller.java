package view.tabs.tab5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import presenter.ImagesService;
import sample.Main;
import view.Utils;

import java.io.IOException;

public class Tab5Controller extends Tab {

    private int maxMaskSize = 32;

    private int gaussianFilterDefaultStd = 80;

    private ImagesService imagesService;

    @FXML
    public TextField gaussianFilterSD;

    @FXML
    public TextField averageTextField;

    @FXML
    public TextField medianTextField;

    @FXML
    public TextField weightedMedianWeightTextField;

    @FXML
    public TextField weightedMedianTextField;

    @FXML public TextField anisotropicLambda;
    @FXML public TextField anisotropicIterations;
    @FXML public TextField anisotropicSigma;
    @FXML public TextField isotropicLambda;
    @FXML public TextField isotropicIterations;
    @FXML public TextField isotropicSigma;
    @FXML public TextField bilateralSds;
    @FXML public TextField bilateralSdr;
    @FXML public TextField bilateralMaskSize;

    public Tab5Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_5.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("Error loading tab_5.fxml", e);
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
    protected  void anisotropicLorentz(ActionEvent event) {
        double lambda = Utils.dsanitizeNumberInput(anisotropicLambda.getText(), Utils.L-1);
        int iterations = Utils.sanitizeNumberInput(anisotropicIterations.getText(), Utils.L-1);
        double sigma = Utils.dsanitizeNumberInput(anisotropicSigma.getText(), Utils.L-1);

        if(lambda == -1 || iterations == -1 || sigma == -1) {
            return;
        }
        imagesService.anisotropicLorentz(lambda, iterations, sigma);
    }
    @FXML
    protected  void anisotropicLeclerc(ActionEvent event) {
        double lambda = Utils.dsanitizeNumberInput(anisotropicLambda.getText(), Utils.L-1);
        int iterations = Utils.sanitizeNumberInput(anisotropicIterations.getText(), Utils.L-1);
        double sigma = Utils.dsanitizeNumberInput(anisotropicSigma.getText(), Utils.L-1);

        if(lambda == -1 || iterations == -1 || sigma == -1) {
            return;
        }
        imagesService.anisotropicLeclerc(lambda, iterations, sigma);
    }
    @FXML
    protected  void isotropicLorentz(ActionEvent event) {
        double lambda = Utils.dsanitizeNumberInput(isotropicLambda.getText(), Utils.L-1);
        int iterations = Utils.sanitizeNumberInput(isotropicIterations.getText(), Utils.L-1);
        double sigma = Utils.dsanitizeNumberInput(isotropicSigma.getText(), Utils.L-1);

        if(lambda == -1 || iterations == -1 || sigma == -1) {
            return;
        }
        imagesService.isotropicLorentz(lambda, iterations, sigma);
    }

    @FXML
    protected void isotropicLeclerc(ActionEvent event) {
        double lambda = Utils.dsanitizeNumberInput(isotropicLambda.getText(), Utils.L-1);
        int iterations = Utils.sanitizeNumberInput(isotropicIterations.getText(), Utils.L-1);
        double sigma = Utils.dsanitizeNumberInput(isotropicSigma.getText(), Utils.L-1);

        if(lambda == -1 || iterations == -1 || sigma == -1) {
            return;
        }
        imagesService.isotropicLeclerc(lambda, iterations, sigma);
    }

    // This should go on tab 3
    @FXML protected void bilateralFilter(ActionEvent event) {
        double sds = Utils.dsanitizeNumberInput(bilateralSds.getText(), Utils.L-1);
        double sdr = Utils.dsanitizeNumberInput(bilateralSdr.getText(), Utils.L-1);
        int maskSize = Utils.sanitizeNumberInput(bilateralMaskSize.getText(), 31);
        if(sds == -1 || sdr == -1 || maskSize == -1 || maskSize % 2 == 0) {
            return;
        }
        imagesService.bilateralFilter(sds, sdr, maskSize);
    }
}
