package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
                    Alert alert = new Alert(Alert.AlertType.WARNING);

                    alert.setTitle("Atenção");
                    alert.setHeaderText("É necessário selecionar um paciente!");
                    alert.show();
                }
            }
        });

        txtSearch.setOnKeyReleased((KeyEvent e) -> {
            table.setItems(searchPatients());
        });
        
        btnSearch.setOnMouseClicked((MouseEvent e)->{          
            table.setItems(searchPatients());
            
        });
        
        imgSearch.setOnMouseClicked((MouseEvent e)->{          
            table.setItems(searchPatients());
        });
    }

    /**
     * Preenche as tabelas com as informações recebidas.
     */
    public void initTable() {
        clmId.setCellValueFactory(new PropertyValueFactory("deviceId"));
        clmName.setCellValueFactory(new PropertyValueFactory("name"));
        clmSeriousCondition.setCellValueFactory(new PropertyValueFactory("isSeriousCondition"));

        table.setItems(updateTable());
    }

    /**
     * Atualiza os campos das tabelas.
     *
     * @return ObservableList<Patient>
     */
    public ObservableList<PatientDevice> updateTable() {
        PatientDevice p1 = new PatientDevice("test A", "123.123.123.123");
        PatientDevice p2 = new PatientDevice("test B", "456.123.456.456");
        PatientDevice p3 = new PatientDevice("test C", "189.789.789.789");
        PatientDevice p4 = new PatientDevice("test D", "000.000.000.000");

        ArrayList<PatientDevice> tempList = new ArrayList<>();

        tempList.add(p1);
        tempList.add(p2);
        tempList.add(p3);
        tempList.add(p4);

        patients = FXCollections.observableArrayList(tempList);

        return patients;
    }

    /**
     * Busca os pacientes pelo nome.
     *
     * @return ObservableList<Patient>
     */
    private ObservableList<PatientDevice> searchPatients() {
        ObservableList<PatientDevice> clientSearch = FXCollections.observableArrayList();

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getName().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
                clientSearch.add(patients.get(i));
            } else if (patients.get(i).getDeviceId().startsWith(txtSearch.getText())) {
                clientSearch.add(patients.get(i));
            }
        }

        return clientSearch;
    }
}
