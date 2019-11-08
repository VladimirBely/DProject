package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.Classes.Suppliers;
import sample.DB.DataAccessObject;

public class SuppliersController {
    @FXML
    private TextField Adress_TF;

    @FXML
    private TextField Name_TF;

    @FXML
    private TextField Telephone_TF;

    @FXML
    private Button Add_btn;

    @FXML
    private Button Edit_btn;

    @FXML
    private Button Delete_btn;

    @FXML
    private TableView<Suppliers> Suppliers_TV;

    @FXML
    private TableColumn<Suppliers, String> Name_col;

    @FXML
    private TableColumn<Suppliers, String> Adress_col;

    @FXML
    private TableColumn<Suppliers, String> Telephone_col;

    @FXML
    private TableColumn<Suppliers, Integer> ID_col;

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

    @FXML
    void initialize() {

        Add_btn.setGraphic(Add_img);
        Add_btn.setOnAction(event -> {
            if (!validateFields()){
                return;
            }
            saveSupplier();
        });

        Edit_btn.setGraphic(Edit_img);
        Edit_btn.setOnAction(event -> {
            if (!editIsSelected()){
                return;
            }
            ADD = false;
            EDIT = true;
            editSuppliers();
        });

        Delete_btn.setGraphic(Delete_img);
        Delete_btn.setOnAction(event -> {
            if (!deleteIsSelected()){
                return;
            }
            deleteSupplier();
        });

        refreshSuppliersTable();

    }

    private boolean validateFields(){
        if (Name_TF.getText().isEmpty() || Adress_TF.getText().isEmpty() || Telephone_TF.getText().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }
        return true;
    }

    private boolean editIsSelected(){
        if (Suppliers_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите редактировать!");
            return false;
        }

        return true;
    }

    private boolean deleteIsSelected(){
        if (Suppliers_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private void saveSupplier() {
        name = Name_TF.getText();
        adress = Adress_TF.getText();
        telephone = Telephone_TF.getText();

        if (EDIT) {
            query = "UPDATE Suppliers SET name_sup = '" + name + "', adress_sup = '" + adress + "',  telephone_sup = '" + telephone + "' WHERE Suppliers.id=" + ID;
        } else if (ADD) {
            query = "INSERT INTO Suppliers(name_sup, adress_sup, telephone_sup)" +
                    " VALUES('" + name + "', '" + adress + "', '" + telephone + "');";
        }

        dao.saveData(query);
        Name_TF.clear();
        Adress_TF.clear();
        Telephone_TF.clear();
        refreshSuppliersTable();
        ADD = true;
        EDIT = false;
    }

    private void deleteSupplier() {
        Suppliers selected = Suppliers_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId_sup().get();
        query = "DELETE FROM Suppliers WHERE Suppliers.id =" + ID;
        dao.saveData(query);
        refreshSuppliersTable();
    }

    private void editSuppliers() {
        Suppliers selected = Suppliers_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId_sup().get();
        Name_TF.setText(selected.getName_sup().get());
        Adress_TF.setText(selected.getAdress_sup().get());
        Telephone_TF.setText(selected.getTelephone_sup().get());

    }

    private void refreshSuppliersTable() {
        initSuppliersTable();
        query = "SELECT name_sup, telephone_sup, adress_sup, Suppliers.id FROM Suppliers";
        Suppliers_TV.setItems(dao.getSuppliersData(query));
    }

    private void initSuppliersTable() {
        Name_col.setCellValueFactory(cellData -> cellData.getValue().getName_sup());
        Telephone_col.setCellValueFactory(cellData -> cellData.getValue().getTelephone_sup());
        Adress_col.setCellValueFactory(cellData -> cellData.getValue().getAdress_sup());
        ID_col.setCellValueFactory(cellData -> cellData.getValue().getId_sup().asObject());
    }
}
