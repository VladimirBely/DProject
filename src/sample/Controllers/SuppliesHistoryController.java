package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.Classes.SuppliesHistory;
import sample.DB.DataAccessObject;

import java.sql.Date;

public class SuppliesHistoryController {


    @FXML
    private TableView<SuppliesHistory> SupplyH_TV;

    @FXML
    private TableColumn<SuppliesHistory, Integer> ID_supply_col;

    @FXML
    private TableColumn<SuppliesHistory, String> Product_col;

    @FXML
    private TableColumn<SuppliesHistory, String> Cateogory_col;

    @FXML
    private TableColumn<SuppliesHistory, Integer> Amount_col;

    @FXML
    private TableColumn<SuppliesHistory, String> Supplier_col;

    @FXML
    private TableColumn<SuppliesHistory, Date> Date_col;

    @FXML
    private ComboBox<Integer> ID_CB;

    @FXML
    private TextField Product_TF1;

    @FXML
    private ImageView Search_img;

    @FXML
    private ImageView Update_img;

    @FXML
    private DatePicker Date_start_DP;

    @FXML
    private DatePicker Date_finish_DP;

    @FXML
    private Button Update_btn;

    @FXML
    private Button Search_btn;

    @FXML
    private Button Excel_btn;

    @FXML
    private ImageView Excel_img;


    private int ID;
    private DataAccessObject dao;
    private String query;
    private String product;
    private Date date_start;
    private Date date_finish;


    @FXML
    void initialize() {
        dao = new DataAccessObject();

        Excel_btn.setGraphic(Excel_img);
        Excel_btn.setOnAction(event -> {
            if (ID_CB.getSelectionModel().isEmpty() && Product_TF1.getText().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null) {
                getAllSupplies();
            }else if(Product_TF1.getText().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null){
                getReportByID();
            }else if (ID_CB.getSelectionModel().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null){
                getReportByProduct();
            }else if (ID_CB.getSelectionModel().isEmpty() && Product_TF1.getText().isEmpty()){
                getReportByPeriod();
            }
        });

        Search_btn.setGraphic(Search_img);
        Search_btn.setOnAction(event -> {
            if (Date_start_DP.getValue() != null || Date_finish_DP.getValue() != null){
                SearchByDate();
            }else if (Product_TF1.getText().isEmpty()){
                Search();
            }else {
                SearchByProduct();
            }
        });
        Update_btn.setGraphic(Update_img);
        Update_btn.setOnAction(event -> {
            refreshSupplyHistory();
            Date_start_DP.setValue(null);
            Date_finish_DP.setValue(null);
            Product_TF1.clear();
            ID_CB.getSelectionModel().clearSelection();
        });
        ID_CB.setOnMouseClicked(event -> initSupplyIdComboBox());
        refreshSupplyHistory();
        initSupplyIdComboBox();

    }

    private void getReportByPeriod() {
        query = "SELECT * FROM Supplies_view WHERE date_supply BETWEEN '" + date_start + "' AND '" + date_finish + "'";
        dao.getSupplies(query, "Поставки за период " + date_start + " - " +date_finish);
    }

    private void getReportByProduct() {
        query = "SELECT * FROM Supplies_view WHERE name_product = '" + product + "' ";
        dao.getSupplies(query, "Поставки по товару " + product);
    }

    private void getReportByID() {
        query = "SELECT * FROM Supplies_view WHERE id = '" + ID + "'";
        dao.getSupplies(query, "Поставка №" + ID);
    }

    private void getAllSupplies() {
        query = "SELECT * FROM Supplies_view";
        dao.getSupplies(query, "Все поставки");
    }

    private void Search(){
        ID = ID_CB.getSelectionModel().getSelectedItem();
        initSupplyHistoryTable();
        query = "SELECT * FROM Supplies_view where id = '" + ID + "' ";
        SupplyH_TV.setItems(dao.getSuppliesHistoryData(query));
    }

    private void SearchByDate(){
       date_start = Date.valueOf(Date_start_DP.getValue());
       date_finish = Date.valueOf(Date_finish_DP.getValue());
       query = "SELECT * FROM Supplies_view WHERE date_supply BETWEEN '"+ date_start+ "' AND '" + date_finish + "'";
       SupplyH_TV.setItems(dao.getSuppliesHistoryData(query));
    }

    private void SearchByProduct(){
        product = Product_TF1.getText();
        query = "SELECT * FROM Supplies_view WHERE name_product = '" + product + "'  ";
        SupplyH_TV.setItems(dao.getSuppliesHistoryData(query));
    }

    private void initSupplyIdComboBox(){
        ID_CB.getSelectionModel().clearSelection();
        query = "SELECT id FROM Supplies_view GROUP BY id ORDER BY id";
        ID_CB.setItems(dao.getSuppliesIDComboBox(query));

    }

    private void initSupplyHistoryTable(){
        ID_supply_col.setCellValueFactory(cellData -> cellData.getValue().getID_Supply().asObject());
        Product_col.setCellValueFactory(cellData -> cellData.getValue().getProduct());
        Cateogory_col.setCellValueFactory(cellData -> cellData.getValue().getCategory());
        Amount_col.setCellValueFactory(cellData -> cellData.getValue().getAmount().asObject());
        Supplier_col.setCellValueFactory(cellData -> cellData.getValue().getSupplier());
        Date_col.setCellValueFactory((cellData) -> cellData.getValue().getDate_supply());

    }

    private void refreshSupplyHistory(){
        initSupplyHistoryTable();
        query = "Select * FROM Supplies_view";
        SupplyH_TV.setItems(dao.getSuppliesHistoryData(query));
    }




}
