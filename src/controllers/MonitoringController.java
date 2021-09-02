package controllers;

import client.MonitoringClient;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.PatientDevice;

/**
 *
 * @author Allan Capistrano
 */
public class MonitoringController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<PatientDevice> table;

    @FXML
    private TableColumn<PatientDevice, String> clmId;

    @FXML
    private TableColumn<PatientDevice, String> clmName;

    @FXML
    private TableColumn<PatientDevice, String> clmSeriousCondition;

    @FXML
    private Label lblDeviceId;

    @FXML
    private Label lblBodyTemperature;

    @FXML
    private Label lblBloodOxygenation;

    @FXML
    private Label lblHeartRate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRespiratoryFrequency;

    @FXML
    private Label lblBloodPressure;

    @FXML
    private Label lblSeriousCondition;

    private PatientDevice selected, temp00;
    private ObservableList<PatientDevice> patients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object clicked) {
                selected = (PatientDevice) clicked;

                if (selected != null) {
                    temp00 = selected;
                    setPatientDeviceValues();
                }
//                } else {
//                    setPatientDeviceValues("");
//                }
            }
        });

        txtSearch.setOnKeyReleased((KeyEvent e) -> {
            table.setItems(searchPatients());
        });

        btnSearch.setOnMouseClicked((MouseEvent e) -> {
            table.setItems(searchPatients());

        });

        imgSearch.setOnMouseClicked((MouseEvent e) -> {
            table.setItems(searchPatients());
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (temp00 != null) {
                                Socket conn = new Socket("localhost", 12244);

                                table.setItems(updateTable(conn));

                                setPatientDeviceValues();
                            }
                        } catch (IOException ioe) {
                            System.err.println("Erro ao requisitar a lista de "
                                    + "dispositivos dos pacientes.");
                            System.out.println(ioe);
                        }
                    }
                };
                
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        System.err.println("Não foi parar a Thread");
                        System.out.println(ie);
                    }
                    
                    Platform.runLater(updater); // Atualizar na thread main
                }
            }

        });
        
        thread.setDaemon(true); //Finalizar a thread quando o programa parar
        thread.start();
    }

    /**
     * Preenche as tabelas com as informações recebidas.
     */
    public void initTable() {
        try {
            Socket conn = new Socket("localhost", 12244);

            clmId.setCellValueFactory(new PropertyValueFactory("deviceId"));
            clmName.setCellValueFactory(new PropertyValueFactory("name"));
            clmSeriousCondition.setCellValueFactory(
                    new PropertyValueFactory("isSeriousCondition")
            );

            table.setItems(updateTable(conn));
        } catch (IOException ioe) {
            System.err.println("Erro de Entrada/Saída");
            System.out.println(ioe);
        }
    }

    /**
     * Atualiza os campos das tabelas.
     *
     * @return ObservableList<Patient>
     */
    public ObservableList<PatientDevice> updateTable(Socket conn) {
        ArrayList<PatientDevice> temp = MonitoringClient.requestPatients(conn);

        if (temp != null) {
            patients = FXCollections.observableArrayList(temp);
        } else {
            callAlert("Erro", "Erro ao tentar atualizar a tabela",
                    AlertType.ERROR);
        }
        
        if (temp00 != null) {
            for (PatientDevice patientDevice: temp) {
                if (patientDevice.getDeviceId().equals(temp00.getDeviceId())) {
                    temp00 = patientDevice;

                    break;
                }
            }
        }

        return patients;
    }

    /**
     * Busca os pacientes pelo nome.
     *
     * @return ObservableList<Patient>
     */
    private ObservableList<PatientDevice> searchPatients() {
        ObservableList<PatientDevice> clientSearch
                = FXCollections.observableArrayList();

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getName().toLowerCase().contains(
                    txtSearch.getText().toLowerCase())) {
                clientSearch.add(patients.get(i));
            } else if (patients.get(i).getDeviceId().startsWith(
                    txtSearch.getText())) {
                clientSearch.add(patients.get(i));
            }
        }

        return clientSearch;
    }

    /**
     * Mostra na tela as informações de um paciente.
     */
    public void setPatientDeviceValues() {
        lblDeviceId.setText(temp00.getDeviceId());
        lblName.setText(temp00.getName());
        lblBodyTemperature.setText(String.valueOf(temp00.getBodyTemperature()));
        lblRespiratoryFrequency.setText(String.valueOf(temp00.getRespiratoryFrequency()));
        lblBloodOxygenation.setText(String.valueOf(temp00.getBloodOxygenation()));
        lblBloodPressure.setText(String.valueOf(temp00.getBloodPressure()));
        lblHeartRate.setText(String.valueOf(temp00.getHeartRate()));
        lblSeriousCondition.setText(temp00.isIsSeriousCondition() ? "Sim" : "Não");
    }

    /**
     * Mostra na tela as informações de um paciente.
     *
     * @param text String - Texto que se deseja mostrar.
     */
    public void setPatientDeviceValues(String text) {
        lblDeviceId.setText(text);
        lblName.setText(text);
        lblBodyTemperature.setText(text);
        lblRespiratoryFrequency.setText(text);
        lblBloodOxygenation.setText(text);
        lblBloodPressure.setText(text);
        lblHeartRate.setText(text);
        lblSeriousCondition.setText(text);
    }

    /**
     * Mostra uma mensagem de alerta na tela.
     *
     * @param title String - Título do alerta.
     * @param text String - Mensagem que será exibida.
     * @param alertType AlertType - Tipo do alerta que será enviado.
     */
    public void callAlert(String title, String text, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.show();
    }
}
