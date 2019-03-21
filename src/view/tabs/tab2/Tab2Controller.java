package view.tabs.tab2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import presenter.ImagesService;
import sample.Main;

import java.io.IOException;

public class Tab2Controller extends Tab {

    private ImagesService imagesService;

    @FXML
    public Text errorsText;

    public Tab2Controller() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab_2.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException("");
        }
        imagesService = Main.getImagesService();
        imagesService.setTab2Controller(this);
    }

    public void setErrorsText(String s) {
        errorsText.setText(s);
    }

    @FXML
    protected void addImages(ActionEvent event) {
        imagesService.addImages();
    }

    @FXML
    protected void subtractImages(ActionEvent event) {
        imagesService.subtractImages();
    }

    @FXML
    protected void drc(ActionEvent event) {
        imagesService.drc();
    }
}
