package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.Classes.OrderDetails;
import sample.Classes.Orders;
import sample.DB.DataAccessObject;

import java.sql.Date;

@SuppressWarnings("ALL")
public class OrderController {
    @FXML
    private ComboBox<String> Client_CB;

    @FXML
    private DatePicker Date_DP;

    @FXML
    private TextField Discount_TF;

    @FXML
    private TextField ID_TF;

    @FXML
    private TableView<Orders> OrderTV;

    @FXML
    private TableColumn<Orders, Integer> ID_col;

    @FXML
    private TableColumn<Orders, String> Client_col;

    @FXML
    private TableColumn<Orders, Date> Date_col;

    @FXML
    private TableColumn<Orders, Double> Discount_col;

    @FXML
    private TableColumn<Orders, Double> Totalcost_col;


    @FXML
    private Button Add_btn;

    @FXML
    private Button Delete_btn;

    @FXML
    private ComboBox<String> Product_CB;

    @FXML
    private TextField Amount_TF;

    @FXML
    private ComboBox<Integer> ID_Send_CB;

    @FXML
    private TableView<OrderDetails> OrderDetails_TV;

    @FXML
    private TableColumn<OrderDetails, String> Product_col;

    @FXML
    private TableColumn<OrderDetails, Integer> Order_col;

    @FXML
    private TableColumn<OrderDetails, Integer> Amount_col;

    @FXML
    private TableColumn<OrderDetails, Double> Price_column;

    @FXML
    private TableColumn<OrderDetails, Integer> Id_column;

    @FXML
    private Button OD_Add_btn;

    @FXML
    private Button OD_Delete_btn;

    @FXML
    private Button Done_btn;

    @FXML
    private ImageView Done_image;



    private String product;
    private String idsend;
    private Integer amount;
    private Double price;
    private Date date;
    private String client;
    private Double discount;
    private Double totalcost;
    private int ID;
    private int ODID;
    private double sum = 0;
    private DataAccessObject dao;
    private String query;
    private boolean EDIT = false;
    private boolean ADD = true;


    @FXML
    void initialize() {

        this.dao = new DataAccessObject();
        Add_btn.setOnAction(event -> {
            if (!validateOrder()){
                return;
            }
            saveOrders();
        });
        OD_Add_btn.setOnAction(event -> {
            if (!validateOrderDetails()){
                return;
            }
            saveOrderDetails();
            refreshOrder();
        });

        Delete_btn.setOnAction(event -> {
            if (!OrdersIsSelected()){
                return;
            }
            deleteSending();
            Done();
        });
        OD_Delete_btn.setOnAction(event -> {
            if (!OrderDetailsIsSelected()){
                return;
            }
            deleteSendingDetails();
            refreshOrderDetails();
            refreshOrder();
        });

        Done_btn.setOnAction(event -> Done());
        Product_CB.setOnMouseClicked(event -> initProductComboBox());
        ID_Send_CB.setOnMouseClicked(event -> initOrderComboBox());
        Client_CB.setOnMouseClicked(event -> initClientsCombobox());

        initClientsCombobox();

        initProductComboBox();

        initOrderComboBox();

    }

    private boolean validateOrder(){
        if (Client_CB.getSelectionModel().isEmpty() || Date_DP.getValue().toString().isEmpty() || Discount_TF.getText().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }

        return true;
    }


    private boolean validateOrderDetails(){
        if (Product_CB.getSelectionModel().isEmpty() || ID_Send_CB.getSelectionModel().isEmpty() || Amount_TF.getText().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Заполните все поля!");
            return false;
        }

        return true;
    }

    private boolean OrdersIsSelected(){
        if (OrderTV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private boolean OrderDetailsIsSelected(){
        if (OrderDetails_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private void saveOrders() {

        client = String.valueOf(Client_CB.getSelectionModel().getSelectedItem());
        date = Date.valueOf(Date_DP.getValue());
        discount = Double.valueOf(Discount_TF.getText());

        if (EDIT) {
            query = "BEGIN; " +
                    "UPDATE Sending SET id_destination = (SELECT Objects.id FROM Objects WHERE name_ = '" + client + "'),  date_sending = '" + date + "',  discount = '" + discount + "' " +
                    " WHERE Sending.id=" + ID + "; COMMIT;";
        } else if (ADD) {
            query = ("INSERT INTO Sending( id_destination, date_sending, discount)" +
                    " VALUES( " +
                    "(Select Objects.id from Objects where name_ = '" + client + "'), '" + date + "', '" + discount + "')");
        }
        dao.saveData(query);
        Client_CB.getSelectionModel().clearSelection();
        Date_DP.getEditor().clear();
        Discount_TF.clear();
        refreshOrder();
        ADD = true;
        EDIT = false;
    }

    private void saveOrderDetails(){
        product = Product_CB.getSelectionModel().getSelectedItem();
        idsend = String.valueOf(ID_Send_CB.getSelectionModel().getSelectedItem());
        amount = Integer.valueOf(Amount_TF.getText());


        if (EDIT){
            query = "UPDATE Sending_details SET id_product = (SELECT Products.id FROM Products WHERE name_product = '" + product + "'), id_sending = '" + idsend + "', amount = '" + amount + "' WHERE Sending_details.id = " + ODID;

        } else if (ADD) {
            query =(
                    "Insert into Sending_details (id_product, id_sending, amount, price_by_unit) "
                            + "values("
                            + "( Select Products.id from Products where name_product = '" + product + "' ) , "
                            + "( Select Sending.id from Sending where id = '" + idsend + "' ), '" + amount + "' , (Select price from Products where Products.id = (Select Products.id from Products where name_product = '" + product + "'))) ");


        }
        dao.saveData(query);
        Product_CB.getSelectionModel().clearSelection();
        ID_Send_CB.getSelectionModel().clearSelection();
        Amount_TF.clear();
        refreshOrderDetails();
        refreshOrder();
        ADD = true;
        EDIT = false;
    }


    private void deleteSending() {
        Orders selected = this.OrderTV.getSelectionModel().getSelectedItem();
        ID = selected.getSID().get();
        query = "DELETE FROM Sending WHERE Sending.id = " + ID;
        dao.saveData(query);
        refreshOrder();
    }

    private void deleteSendingDetails(){
        OrderDetails selected = this.OrderDetails_TV.getSelectionModel().getSelectedItem();
        ODID = selected.getSDID().get();
        query = "DELETE FROM Sending_details WHERE Sending_details.id = " + ODID;
        dao.saveData(query);
        refreshOrderDetails();
    }

    private void refreshOrder(){
        initOrderTable();
        query = "SELECT Sending.id, Objects.name_, Sending.date_sending, Sending.discount, Sending.totalcost FROM Sending JOIN Objects ON id_destination = Objects.id ORDER BY Sending.id DESC LIMIT 1";
        OrderTV.setItems(dao.getOrdersData(query));
    }

    private void refreshOrderDetails(){
        initOrderDetailsTable();
        query = " Select name_product, id_sending, Sending_details.amount, Sending_details.price_by_unit, Sending_details.id from Sending_details\n" +
                "join products on id_product = Products.id\n" +
                "Where id_sending in (SELECT Sending.id From Sending Order by id DESC Limit 1)" +
                "ORDER BY Sending_details.id";
        OrderDetails_TV.setItems(dao.getOrderDetailsData(query));
    }

    private void displayByID(){
        ODID = ID_Send_CB.getSelectionModel().getSelectedItem();
        query = "SELECT * FROM Sending_details WHERE Sending.details.id_sending = '" + ODID + "' ";
        OrderDetails_TV.setItems(dao.getOrderDetailsData(query));
    }

    private void initOrderDetailsTable() {
        Product_col.setCellValueFactory((cell) -> cell.getValue().getProduct());
        Order_col.setCellValueFactory((cell) -> cell.getValue().getId_send().asObject());
        Amount_col.setCellValueFactory((cell) -> cell.getValue().getAmount().asObject());
        Price_column.setCellValueFactory((cell) -> cell.getValue().getPrice().asObject());
        Id_column.setCellValueFactory((cell) -> cell.getValue().getSDID().asObject());

    }

    private void initOrderTable() {
        ID_col.setCellValueFactory((cell) -> cell.getValue().getSID().asObject());
        Client_col.setCellValueFactory((cell) -> cell.getValue().getClient());
        Date_col.setCellValueFactory((cell) -> cell.getValue().getDate());
        Discount_col.setCellValueFactory((cell) -> cell.getValue().getDiscount().asObject());
        Totalcost_col.setCellValueFactory((cell) -> cell.getValue().getTotalcost().asObject());

    }

    private void initClientsCombobox() {
        Client_CB.getSelectionModel().clearSelection();
        query = "SELECT name_ FROM Objects order by Objects.id";
        Client_CB.setItems(dao.getObjectComboBox(query));
    }

    private void initProductComboBox(){
        Product_CB.getSelectionModel().clearSelection();
        query = "SELECT name_product from Products ORDER BY Products.id";
        Product_CB.setItems(dao.getProductComboBox(query));
    }

    private void initOrderComboBox(){
        ID_Send_CB.getSelectionModel().clearSelection();
        query = "SELECT Sending.id FROM Sending ORDER BY id";
        ID_Send_CB.setItems(dao.getOrdersComboBox(query));
    }

    private void Done(){
        OrderTV.getItems().clear();
        OrderDetails_TV.getItems().clear();
    }

}
