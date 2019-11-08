package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Classes.Supplies;
import sample.Classes.Supply_Details;
import sample.DB.DataAccessObject;

import java.sql.Date;

public class SupplyRegistrationController {
    @FXML
    private TableView<Supplies> Supplies_TV;

    @FXML
    private TableColumn<Supplies, Integer> ID_supply_col;

    @FXML
    private TableColumn<Supplies, String> Supplier_col;

    @FXML
    private TableColumn<Supplies, Date> Date_supply_col;

    @FXML
    private ComboBox<String> Supplier_CB;

    @FXML
    private DatePicker Date_supply_DP;

    @FXML
    private Button Delete_Supply_btn;

    @FXML
    private Button Add_supply_btn;

    @FXML
    private TableView<Supply_Details> SupplyDetails_TV;

    @FXML
    private TableColumn<Supply_Details, String> Product_col;

    @FXML
    private TableColumn<Supply_Details, Integer> Supply_ID_col;

    @FXML
    private TableColumn<Supply_Details, Integer> Amount_col;

    @FXML
    private TableColumn<Supply_Details, Integer> ID_SD_col;


    @FXML
    private ComboBox<String> ID_supply_CB;

    @FXML
    private ComboBox<String> Product_CB;

    @FXML
    private TextField Amount_TF;

    @FXML
    private Button Delete_sd_btn;

    @FXML
    private Button Add_sd_btn;

    @FXML
    private Button Done_btn;

    private String product;
    private String id_supp;
    private Integer amount;
    private int SupplyDetails_ID;

    private Date date_supp;
    private String supplier;
    private int ID_suply;

    private DataAccessObject dao;
    private String query;
    private boolean EDIT = false;
    private boolean ADD = true;

    @FXML
    void initialize() {
        dao = new DataAccessObject();
        Add_supply_btn.setOnAction(event -> {
            if (!validateSupplies()){
                return;
            }
            saveSupplies();
        });
        Add_sd_btn.setOnAction(event -> {
            if (!validateSupply_details()){
                return;
            }
            saveSupplyDetails();
            refreshSupplies();
        });
        Delete_Supply_btn.setOnAction(event -> {
            if (!deleteIsSelected()){
                return;
            }
            deleteSupplies();
            Done();
        });
        Delete_sd_btn.setOnAction(event -> {
            if (!deleteIsSelected1()){
                return;
            }
            deleteSupplyDetails();
        });
        Supplier_CB.setOnMouseClicked(event -> initSuppliersCombobox());
        Product_CB.setOnMouseClicked(event -> initProductComboBox());
        ID_supply_CB.setOnMouseClicked(event -> initSuppliesComboBox());

        Done_btn.setOnAction(event -> Done());
        //refreshSupplies();
        //refreshSupplyDetails();

    }


    private boolean validateSupplies(){
        if (Supplier_CB.getSelectionModel().isEmpty() || Date_supply_DP.getValue().toString().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }

        return true;
    }

    private boolean validateSupply_details(){
        if (Product_CB.getSelectionModel().isEmpty() || ID_supply_CB.getSelectionModel().isEmpty() || Amount_TF.getText().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }

        return true;
    }

    private boolean deleteIsSelected(){
        if (Supplies_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private boolean deleteIsSelected1(){
        if (SupplyDetails_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private void deleteSupplies() {
        Supplies selected = Supplies_TV.getSelectionModel().getSelectedItem();
        ID_suply = selected.getID_supply().get();
        query = "DELETE FROM Supplies WHERE Supplies.id = " + ID_suply;
        dao.saveData(query);
        refreshSupplies();
    }

    private void deleteSupplyDetails() {
        Supply_Details selected = SupplyDetails_TV.getSelectionModel().getSelectedItem();
        SupplyDetails_ID = selected.getSupID().get();
        query = "DELETE FROM Supply_details WHERE Supply_details.id = " + SupplyDetails_ID;
        dao.saveData(query);
        refreshSupplyDetails();
        refreshSupplies();
    }

    private void saveSupplies() {

        supplier = Supplier_CB.getSelectionModel().getSelectedItem();
        date_supp = Date.valueOf(Date_supply_DP.getValue());

        if (EDIT) {
            query = "BEGIN; " +
                    "UPDATE Supplies SET id_supplier = (SELECT Suppliers.id FROM Suppliers WHERE name_sup = '" + supplier + "'),  date_supply = '" + date_supp + "' " +
                    " WHERE Supplies.id=" + ID_suply + "; COMMIT;";
        } else if (ADD) {
            query = ("INSERT INTO Supplies(id_supplier, date_supply)" +
                    " VALUES( " +
                    "(Select Suppliers.id from Suppliers where name_sup = '" + supplier + "'), '" + date_supp + "')");
        }
        dao.saveData(query);
        Supplier_CB.getSelectionModel().clearSelection();
        Date_supply_DP.getEditor().clear();

        refreshSupplies();
        ADD = true;
        EDIT = false;
    }

    private void refreshSupplies() {
        initSuppliesTable();
        query = "SELECT Supplies.id, Suppliers.name_sup, Supplies.date_supply FROM Supplies JOIN Suppliers ON id_supplier = Suppliers.id ORDER BY Supplies.id DESC LIMIT 1";
        Supplies_TV.setItems(dao.getSuppliesData(query));

    }

    private void initSuppliesTable() {
        ID_supply_col.setCellValueFactory(cellData -> cellData.getValue().getID_supply().asObject());
        Supplier_col.setCellValueFactory(cellData -> cellData.getValue().getSupplier());
        Date_supply_col.setCellValueFactory(cellData -> cellData.getValue().getDate_supply());
    }

    private void saveSupplyDetails(){
        product = Product_CB.getSelectionModel().getSelectedItem();
        id_supp = ID_supply_CB.getSelectionModel().getSelectedItem();
        amount = Integer.valueOf(Amount_TF.getText());

        if (EDIT){
            query = "UPDATE Supply_details SET id_product = (SELECT Products.id FROM Products WHERE name_product = '" + product + "'), id_supply = '" + id_supp + "', amount = '" + amount + "' WHERE Sending_details.id = " + SupplyDetails_ID;

        } else if (ADD) {
            query =(
                    "Insert into Supply_details (id_product, id_supply, amount) "
                            + "values("
                            + "( Select Products.id from Products where name_product = '" + product + "' ) , "
                            + "( Select Supplies.id from Supplies where id = '" + id_supp + "' ),  '" + amount + "') ");

        }
        dao.saveData(query);
        Product_CB.getSelectionModel().clearSelection();
        ID_supply_CB.getSelectionModel().clearSelection();
        Amount_TF.clear();
        refreshSupplyDetails();
        ADD = true;
        EDIT = false;
    }

    private void refreshSupplyDetails() {
        initSupplyDetails();
        query = "SELECT Products.name_product, Supplies.id, Supply_Details.amount, Supply_details.id FROM Supply_details JOIN Products ON id_product = Products.id JOIN Supplies ON id_supply = Supplies.id\n" +
                "WHERE Supply_details.id_supply in (SELECT Supplies.id FROM Supplies ORDER BY id DESC LIMIT 1)";
        SupplyDetails_TV.setItems(dao.getSupplyDetailsData(query));
    }

    private void initSupplyDetails() {
        Product_col.setCellValueFactory(cellData -> cellData.getValue().getProduct_sup());
        Supply_ID_col.setCellValueFactory(cellData -> cellData.getValue().getId_sup().asObject());
        Amount_col.setCellValueFactory(cellData -> cellData.getValue().getAmount_sup().asObject());
    }

    private void initSuppliersCombobox() {
        Supplier_CB.getSelectionModel().clearSelection();
        query = "SELECT name_sup FROM Suppliers order by id";
        Supplier_CB.setItems(dao.getSuppliersComboBox(query));
    }

    private void initProductComboBox(){
        Product_CB.getSelectionModel().clearSelection();
        query = "SELECT name_product from Products";
        Product_CB.getItems().addAll(dao.getProductComboBox(query));
    }

    private void initSuppliesComboBox(){
        ID_supply_CB.getSelectionModel().clearSelection();
        query = "SELECT Supplies.id FROM Supplies ORDER BY id DESC LIMIT 1";
        ID_supply_CB.setItems(dao.getSuppliesComboBox(query));
    }

    private void Done(){
        Supplies_TV.getItems().clear();
        SupplyDetails_TV.getItems().clear();
    }

}
