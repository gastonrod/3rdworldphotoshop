package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenter.ImagesPresenter;

public class Main extends Application {

    private static ImagesPresenter imagesPresenter;
    /*var = (1/(n-1) sum(valores imagen))^1/2
    `* equializacion: s^k = ceil(((sk - smin / 1 - smin) + 0.5) * 255)
     * Aplicar rango dinamico en 1.b
     * gamma E (0,2)
    */

    @Override
    public void start(Stage primaryStage) throws Exception{
        int size = (int)(10.0 * 4.5);
        primaryStage.setWidth(16 * size);
        primaryStage.setHeight(9 * size);
        Parent root = FXMLLoader.load(getClass().getResource("../view/main_stage.fxml"));
        primaryStage.setTitle("Analisis y tratamiento de imagenes");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static ImagesPresenter getImagesPresenter() {
        return imagesPresenter;
    }

    public static void main(String[] args) {
        imagesPresenter = new ImagesPresenter();
        launch(args);
    }
}
