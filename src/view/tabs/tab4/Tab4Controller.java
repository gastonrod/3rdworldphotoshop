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
    @FXML public TextField anisotropicLambda;
    @FXML public TextField anisotropicIterations;
    @FXML public TextField anisotropicSigma;
    @FXML public TextField isotropicLambda;
    @FXML public TextField isotropicIterations;
    @FXML public TextField isotropicSigma;
    @FXML public TextField bilateralSds;
    @FXML public TextField bilateralSdr;
    @FXML public TextField bilateralMaskSize;

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
