package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import models.Patient;

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
    private TableView<Patient> table;

    @FXML
    private TableColumn<Patient, String> clmId;

    @FXML
    private TableColumn<Patient, String> clmName;

    @FXML
    private TableColumn<Patient, String> clmSeriousCondition;

    private Patient selected;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
    }

    public void initTable() {
        clmId.setCellValueFactory(new PropertyValueFactory("deviceId"));
        clmName.setCellValueFactory(new PropertyValueFactory("name"));
        clmSeriousCondition.setCellValueFactory(new PropertyValueFactory("isSeriousCondition"));
        
        table.setItems(updateTable());
    }

    public ObservableList<Patient> updateTable() {
        Patient p1 = new Patient("test1", "123.123.123.123");
        Patient p2 = new Patient("test2", "456.456.456.456");
        Patient p3 = new Patient("test3", "789.789.789.789");
        Patient p4 = new Patient("test4", "000.000.000.000");

        ArrayList<Patient> tempList = new ArrayList<>();
        
        tempList.add(p1);
        tempList.add(p2);
        tempList.add(p3);
        tempList.add(p4);

        patients = FXCollections.observableArrayList(tempList);
        return patients;
    }

}
