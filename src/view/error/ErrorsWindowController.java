package view.error;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class ErrorsWindowController {
    private static final String OK = "Ok";

    private ErrorsWindowController() {}
    public static void newErrorCode(ErrorCodes errorCode) {
        VBox vBox = new VBox();
        Text errorText = new Text(errorCode.getMessage());
        Button okButton = new Button(OK);
        okButton.setOnAction(e-> {
            Stage stage = (Stage)(okButton.getScene().getWindow());
            stage.close();
        });
        vBox.getChildren().addAll(errorText, okButton);
        Stage stage = new Stage();
        stage.setScene(new Scene(vBox));
        stage.show();
    }
}
