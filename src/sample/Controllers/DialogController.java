package sample.Controllers;

import javafx.scene.control.Alert;

public class DialogController {

    public static void showInfoDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void showErrorDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    static void showWarningDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
