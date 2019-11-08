package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.Classes.Clients;
import sample.DB.DataAccessObject;

public class ClientsController {

    @FXML
    private TextField Adress_TF;

    @FXML
    private TextField Name_TF;

    @FXML
    private TextField Telephone_TF;

    @FXML
    private TextField Sum_TF;

    @FXML
    private Button Add_btn;

    @FXML
    private Button Edit_btn;

    @FXML
    private Button Delete_btn;

    @FXML
    private TableView<Clients> Clients_TV;

    @FXML
    private TableColumn<Clients, String> Name_col;

    @FXML
    private TableColumn<Clients, String> Adress_col;

    @FXML
    private TableColumn<Clients, String> Telephone_col;

    @FXML
    private TableColumn<Clients, Double> Deposit_col;

    @FXML
    private TableColumn<Clients, Double> Balance_col;

    @FXML
    private TableColumn<Clients, Integer> ID_col;

    @FXML
    private ImageView Add_img;

    @FXML
    private ImageView Edit_img;

    @FXML
    private ImageView Delete_img;


    private String query;
    private DataAccessObject dao = new DataAccessObject();
    private int ID;
    private boolean EDIT = false;
    private boolean ADD = true;
    private String name;
    private String adress;
    private String telephone;
    private Double deposit;


    @FXML
    void initialize() {

        Add_btn.setGraphic(Add_img);
        Add_btn.setOnAction(event -> {
            if (!validateFields()){
                return;
            }
            saveClients();
        });

        Edit_btn.setGraphic(Edit_img);
        Edit_btn.setOnAction(event -> {
            if (!editIsSelected()){
                return;
            }
            ADD = false;
            EDIT = true;
            editClients();
        });

        Delete_btn.setGraphic(Delete_img);
        Delete_btn.setOnAction(event -> {
            if(!deleteIsSelected()){
                return;
            }
            deleteClients();
        });

        refreshClientsTable();

    }

    private boolean validateFields(){
        if (Name_TF.getText().isEmpty() || Adress_TF.getText().isEmpty() || Telephone_TF.getText().isEmpty() || Sum_TF.getText().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }

        return true;
    }

    private boolean editIsSelected(){
        if (Clients_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите редактировать!");
            return false;
        }

        return true;
    }

    private boolean deleteIsSelected(){
        if (Clients_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }



    private void refreshClientsTable() {
        initClientTable();
        query = "SELECT name_, adress, telephone, deposit, balance, id FROM clients_balance_view";
        Clients_TV.setItems(dao.getClientsData(query));
    }

    private void initClientTable() {
        Name_col.setCellValueFactory((cell) -> cell.getValue().getName());
        Adress_col.setCellValueFactory((cell) -> cell.getValue().getAdress());
        Telephone_col.setCellValueFactory((cell) -> cell.getValue().getTelephone());
        Deposit_col.setCellValueFactory((cell -> cell.getValue().getDeposit().asObject()));
        Balance_col.setCellValueFactory((cell -> cell.getValue().getBalance().asObject()));
        ID_col.setCellValueFactory((cell) -> cell.getValue().getId().asObject());
    }

    private void deleteClients() {
        Clients selected = this.Clients_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId().get();
        query = "DELETE FROM Objects WHERE Objects.id =" + ID;
        dao.saveData(this.query);
        refreshClientsTable();
    }

    private void editClients() {
        Clients selected = this.Clients_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId().get();
        Name_TF.setText(selected.getName().get());
        Adress_TF.setText(selected.getAdress().get());
        Telephone_TF.setText(selected.getTelephone().get());
        Sum_TF.setText(String.valueOf((Double)selected.getDeposit().get()));

    }

    private void saveClients() {
        name = Name_TF.getText();
        adress = Adress_TF.getText();
        telephone = Telephone_TF.getText();
        deposit = Double.valueOf(Sum_TF.getText());

        if (EDIT) {
            this.query = "UPDATE Objects SET name_ = '" + name + "', adress = '" + adress + "',  telephone = '" + telephone + "', deposit = '" + deposit + "' WHERE Objects.id=" + ID;
        } else if (ADD) {
            query = "INSERT INTO Objects(name_, adress, telephone, deposit)" +
                    " VALUES('" + name + "', '" + adress + "', '" + telephone + "', '" + deposit + "');";
        }

       dao.saveData(query);
        Name_TF.clear();
        Adress_TF.clear();
        Telephone_TF.clear();
        Sum_TF.clear();
        refreshClientsTable();
        ADD = true;
        EDIT = false;
    }
}
