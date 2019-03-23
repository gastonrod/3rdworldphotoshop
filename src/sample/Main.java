package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenter.ImagesService;

public class Main extends Application {

    private static ImagesService imagesService;
    /*var = (1/(n-1) sum(valores imagen))^1/2
    `* equializacion: s^k = ceil(((sk - smin / 1 - smin) + 0.5) * 255)
     * Aplicar rango dinamico en 1.b
     * gamma E (0,2)
     *  Antes de contaminar la imagen decidimos que % vamos a contaminar, y los elegimos en forma aleatoria
     *      no contaminamos toda.
     *
     *  Para aplicar ruido:
     *      1. elegimos cant pixeles
     *      2. elegimos variable aleatoria
     *      3. vamos pix por pix aplicando ruido segun
     *  Sal y pimienta:
     *      p1 = 1-p0.
     *      tomas I(i,j) para todos los pixeles
     *
     *  Ruido guseano: tomar n = 2sigma + 1 || 3sigma
     *  Observar que pasa si aplicamos mascara de gaus a un pixel blanco sobre una imagen negra
    */

    @Override
    public void start(Stage primaryStage) throws Exception{
        int size = (int)(10.0 * 4.5);
        primaryStage.setWidth(16.0 * size);
        primaryStage.setHeight(9.0 * size);
        Parent root = FXMLLoader.load(getClass().getResource("../view/main_stage.fxml"));
        primaryStage.setTitle("Analisis y tratamiento de imagenes");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static ImagesService getImagesService() {
        return imagesService;
    }

    public static void main(String[] args) {
        imagesService = new ImagesService();
        launch(args);
    }
}
