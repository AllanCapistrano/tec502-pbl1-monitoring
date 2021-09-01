package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.PatientDevice;

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
    
    @FXML
    private Label lblSeriousCondition;
    
    private static PatientDevice patient;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPatient();
    }
    
    /**
     * Coloca os dados do paciente na interface gráfica.
     */
    public void initPatient() {
        String bodyTemperature = String.valueOf(patient.getBodyTemperature()) + " °C";
        String respiratoryFrequency = String.valueOf(patient.getRespiratoryFrequency()) + " movimentos/minuto";
        String bloodOxygenation = String.valueOf(patient.getBloodOxygenation()) + " %";
        String bloodPressure = String.valueOf(patient.getBloodPressure()) + " mmHg";
        String heartRate = String.valueOf(patient.getHeartRate()) + " batimentos/minuto";
        
        lblName.setText(patient.getName());
        lblBodyTemperature.setText(bodyTemperature);
        lblRespiratoryFrequency.setText(respiratoryFrequency);
        lblBloodOxygenation.setText(bloodOxygenation);
        lblBloodPressure.setText(bloodPressure);
        lblHeartRate.setText(heartRate);
        
        lblSeriousCondition.setText(patient.isIsSeriousCondition() ? "Sim" : "Não");
    }
    
    public static void setPatient(PatientDevice patient) {
        InfoController.patient = patient;
    }
}
