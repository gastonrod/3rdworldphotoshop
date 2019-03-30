package view.components;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import presenter.ImagesService;
import sample.Main;

import java.io.File;


public class ImageAndSaveHBox extends VBox {

    public ImageAndSaveHBox(WritableImage image, int id) {
        ImagesService imagesService = Main.getImagesService();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            File file = new FileChooser().showSaveDialog(getScene().getWindow());
            if (file == null)
                return;
            imagesService.saveImage(file, id);
            });

        Button dockButton = new Button("Open");
        dockButton.setOnAction(e-> {
            imagesService.setImageInSecondWindowAsMain(id);
            Stage stage = (Stage)(dockButton.getScene().getWindow());
            stage.close();
        });

        ImageView img = new ImageView(image);
        HBox buttonsContainer = new HBox();
        buttonsContainer.getChildren().addAll(saveButton, dockButton);
        getChildren().add(img);
        getChildren().add(buttonsContainer);
    }
}

