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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.PatientDevice;

/**
 *
 * @author Allan Capistrano
 */
public class MonitoringController implements Initializable {

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

    private PatientDevice patientClicked, patientSelected;
    private ObservableList<PatientDevice> patients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object clicked) {
                patientClicked = (PatientDevice) clicked;

                if (patientClicked != null) {
                    patientSelected = patientClicked;
                    setPatientDeviceValues();
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (patientSelected != null) {
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

                    /* Atualizar as informações na Thread principal. */
                    Platform.runLater(updater);
                }
            }

        });

        /* Finalizar a thread de requisição quando fechar o programa. */
        thread.setDaemon(true);
        /* Iniciar a thread de requisições. */
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
     * @param conn Socket - Conexão com o servidor.
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

        if (patientSelected != null) {
            for (PatientDevice patientDevice : temp) {
                if (patientDevice.getDeviceId().equals(patientSelected.getDeviceId())) {
                    patientSelected = patientDevice;

                    break;
                }
            }
        }

        return patients;
    }

    /**
     * Mostra na tela as informações de um paciente.
     */
    public void setPatientDeviceValues() {
        lblDeviceId.setText(patientSelected.getDeviceId());
        lblName.setText(patientSelected.getName());
        lblBodyTemperature.setText(String.valueOf(patientSelected.getBodyTemperature()));
        lblRespiratoryFrequency.setText(String.valueOf(patientSelected.getRespiratoryFrequency()));
        lblBloodOxygenation.setText(String.valueOf(patientSelected.getBloodOxygenation()));
        lblBloodPressure.setText(String.valueOf(patientSelected.getBloodPressure()));
        lblHeartRate.setText(String.valueOf(patientSelected.getHeartRate()));
        lblSeriousCondition.setText(patientSelected.isIsSeriousCondition() ? "Sim" : "Não");
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
