package tec502.pbl1.monitoring;

import controllers.InfoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.PatientDevice;

/**
 *
 * @author Allan Capistrano
 */
public class Info extends Application {

    private static Stage stage;

    /**
     * Método construtor
     *
     * @param patient PatientDevice - Paciente selecionado.
     */
    public Info(PatientDevice patient) {
        InfoController.setPatient(patient);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Info.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Informações do Paciente");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Image image = new Image("/images/covid19-monitoring.png");

        stage.getIcons().add(image);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Info.stage = stage;
    }

}
