package view.components;

import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ImagesList {

    private double width = 0;
    private double height = 0;
    private double heighOfButtons = 55;
    ArrayList<ImageAndSaveHBox> prettyImages;
    HBox containerHBox = new HBox();
    public ImagesList(ArrayList<WritableImage> images){
         prettyImages = new ArrayList<>(images.size());
        for(int i = 0; i < images.size(); i++) {
            WritableImage wImg = images.get(i);
            width  += wImg.getWidth();
            height = Math.max(wImg.getHeight() + heighOfButtons, height);
            prettyImages.add(new ImageAndSaveHBox(wImg, i));
        }
        System.out.println(height);
        try {
            containerHBox.getChildren().addAll(prettyImages);
            Stage stage = new Stage();
            Scene scene = new Scene(containerHBox);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    public void addImage(WritableImage image, int i){
        containerHBox.getChildren().add(new ImageAndSaveHBox(image, i));
        Stage s = (Stage)(containerHBox.getScene().getWindow());
        width += image.getWidth();
        height = Math.max(image.getHeight() + heighOfButtons, height);
        System.out.println(height);
        s.setWidth(width);
        s.setHeight(height);
    }
}
