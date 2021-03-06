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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.PatientDevice;

/**
 * Controller de monitoramento.
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
    
    @FXML
    private ImageView imgCloud;

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 12244;
    private final int SLEEP = 8000;

    private PatientDevice patientClicked, patientSelected;
    private ObservableList<PatientDevice> patients
            = FXCollections.observableArrayList();

    private boolean init = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Socket conn = new Socket(IP_ADDRESS, PORT);

            /* Preenche a tabela. */
            initTable(conn);
            
            /* Indica que est?? conectado com o servidor. */
            imgCloud.setImage(new Image("/images/cloud-check.png"));

            conn.close();
        } catch (IOException ioe) {
            System.err.println("Erro ao tentar se conectar com o servidor.");
            System.out.println(ioe);
            
            /* Indica que n??o est?? conectado com o servidor. */
            imgCloud.setImage(new Image("/images/cloud-slash.png"));
        }

        /* Quando clicar em um paciente na tabela. */
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object clicked) {
                patientClicked = (PatientDevice) clicked;

                if (patientClicked != null) {
                    patientSelected = patientClicked;

                    /* Mostra as informa????es do paciente selecionado */
                    setPatientDeviceValues();

                    init = false;
                }
            }
        });

        /* Thread respons??vel por requisitar ao servidor a lista com os 
        dispositivos dos pacientes. */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (patientSelected != null || init) {
                                /* Estabelece a conex??o. */
                                Socket conn = new Socket(IP_ADDRESS, PORT);

                                if (!init) {
                                    /* Atualiza a tabela. */
                                    table.setItems(updateTable(conn));
                                    /* Atualiza as informa????es do paciente 
                                    selecionado */
                                    setPatientDeviceValues();
                                } else {
                                    /* Caso n??o tenha conectado da primeira 
                                        vez, tenta iniciar a tabela novamente.*/
                                    initTable(conn);
                                }
                                
                                /* Indica que est?? conectado com o servidor. */
                                imgCloud.setImage(new Image("/images/cloud-check.png"));

                                /* Finaliza a conex??o. */
                                conn.close();
                            }
                        } catch (IOException ioe) {
                            System.err.println("Erro ao requisitar a lista de "
                                    + "dispositivos dos pacientes.");
                            System.out.println(ioe);
                            
                            /* Indica que n??o est?? conectado com o servidor. */
                            imgCloud.setImage(new Image("/images/cloud-slash.png"));
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException ie) {
                        System.err.println("N??o foi poss??vel parar a Thread");
                        System.out.println(ie);
                    }
                    /* Atualizar as informa????es na Thread principal. */
                    Platform.runLater(updater);
                }
            }
        });

        /* Finalizar a thread de requisi????o quando fechar o programa. */
        thread.setDaemon(true);
        /* Iniciar a thread de requisi????es. */
        thread.start();
    }

    /**
     * Preenche as tabelas com as informa????es recebidas.
     *
     * @param conn Socket - Conex??o com o servidor.
     */
    public void initTable(Socket conn) {
        clmId.setCellValueFactory(new PropertyValueFactory("deviceId"));
        clmName.setCellValueFactory(new PropertyValueFactory("name"));
        clmSeriousCondition.setCellValueFactory(
                new PropertyValueFactory("isSeriousConditionLabel")
        );

        table.setItems(updateTable(conn));
    }

    /**
     * Atualiza os campos das tabelas.
     *
     * @param conn Socket - Conex??o com o servidor.
     * @return ObservableList<Patient>
     */
    public ObservableList<PatientDevice> updateTable(Socket conn) {
        ArrayList<PatientDevice> temp = MonitoringClient.requestPatients(conn);

        if (temp != null) {
            patients = FXCollections.observableArrayList(temp);

            if (patientSelected != null) {
                for (PatientDevice patientDevice : temp) {
                    if (patientDevice.getDeviceId().equals(patientSelected.getDeviceId())) {
                        patientSelected = patientDevice;

                        break;
                    }
                }
            }
        } else if (temp == null && !init) {
            callAlert("Erro", "Erro ao tentar atualizar a tabela",
                    AlertType.ERROR);
        }

        return patients;
    }

    /**
     * Mostra na tela as informa????es de um paciente.
     */
    public void setPatientDeviceValues() {
        lblDeviceId.setText(patientSelected.getDeviceId());
        lblName.setText(patientSelected.getName());
        lblBodyTemperature.setText(
                String.valueOf(patientSelected.getBodyTemperature())
        );
        lblRespiratoryFrequency.setText(
                String.valueOf((int) patientSelected.getRespiratoryFrequency())
        );
        lblBloodOxygenation.setText(
                String.valueOf(patientSelected.getBloodOxygenation())
        );
        lblBloodPressure.setText(
                String.valueOf((int) patientSelected.getBloodPressure())
        );
        lblHeartRate.setText(
                String.valueOf((int) patientSelected.getHeartRate())
        );
        lblSeriousCondition.setText(
                patientSelected.isIsSeriousCondition()
                ? "Sim ("
                + String.format(
                        "%.1f",
                        patientSelected.calculatePatientSeverityLevel()
                ).replace(",", ".")
                + ")"
                : "N??o"
        );
    }

    /**
     * Mostra uma mensagem de alerta na tela.
     *
     * @param title String - T??tulo do alerta.
     * @param text String - Mensagem que ser?? exibida.
     * @param alertType AlertType - Tipo do alerta que ser?? enviado.
     */
    public void callAlert(String title, String text, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.show();
    }
}
