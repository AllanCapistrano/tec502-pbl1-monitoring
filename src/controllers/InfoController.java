package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Allan Capistrano
 */
public class InfoController implements Initializable {

    @FXML
    private Label lblName;

    @FXML
    private Label lblBodyTemperature;

    @FXML
    private Label lblRespiratoryFrequency;

    @FXML
    private Label lblBloodOxygenation;

    @FXML
    private Label lblBloodPressure;

    @FXML
    private Label lblHeartRate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
