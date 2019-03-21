package view.menu.file;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import presenter.ImagesService;
import sample.Main;

import java.io.File;
import java.io.IOException;

public class FileController extends Menu {

    private FileChooser fileChooser;
    private ImagesService imagesPresenter;

    public FileController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("file_menu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        fileChooser = new FileChooser();
        imagesPresenter = Main.getImagesService();
    }

    @FXML
    protected void openMainImage(ActionEvent event) {
        File file = fileChooser.showOpenDialog(imagesPresenter.getWindow());
        if (file == null)
            return;
        imagesPresenter.openImage(file, true);
    }

    @FXML
    protected void saveMainImage(ActionEvent event) {
        File file = fileChooser.showSaveDialog(imagesPresenter.getWindow());
        if (file == null)
            return;
        imagesPresenter.saveCurrentImage(file, true);
    }
    @FXML
    protected void openSecondaryImage(ActionEvent event) {
        File file = fileChooser.showOpenDialog(imagesPresenter.getWindow());
        if (file == null)
            return;
        imagesPresenter.openImage(file, false);
    }

    @FXML
    protected void saveSecondaryImage(ActionEvent event) {
        File file = fileChooser.showSaveDialog(imagesPresenter.getWindow());
        if (file == null)
            return;
        imagesPresenter.saveCurrentImage(file, false);
    }
}
