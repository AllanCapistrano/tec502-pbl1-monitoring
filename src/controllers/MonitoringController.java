package controllers;

import client.MonitoringClient;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.PatientDevice;
import tec502.pbl1.monitoring.Info;

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

    private PatientDevice selected;
    private ObservableList<PatientDevice> patients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object clicked) {
                selected = (PatientDevice) clicked;

                if (selected != null) {
                    Info newWindow = new Info(selected);

                    try {
                        newWindow.start(new Stage());
                    } catch (Exception ex) {
                        System.err.println("Erro ao tentar exibir as informações do paciente.");
                    }
                } else {
                    callAlert("Atenção", "É necessário selecionar um paciente!", AlertType.WARNING);
                }
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

    // NÃO SEI SE É NECESSÁRIO.
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
