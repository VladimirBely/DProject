package sample.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.Classes.OrdersHistory;
import sample.DB.DataAccessObject;

import java.sql.Date;

public class OrderHistoryController {


    @FXML
    private TableView<OrdersHistory> OrderH_TV;

    @FXML
    private TableColumn<OrdersHistory, Integer> ID_order_col;

    @FXML
    private TableColumn<OrdersHistory, String> Product_col;

    @FXML
    private TableColumn<OrdersHistory, String> Catogory_col;

    @FXML
    private TableColumn<OrdersHistory, Double> Price_col;

    @FXML
    private TableColumn<OrdersHistory, Integer> Amount_col;

    @FXML
    private TableColumn<OrdersHistory, String> Client_col;

    @FXML
    private TableColumn<OrdersHistory, Date> Date_col;

    @FXML
    private TableColumn<OrdersHistory, Double> Discount_col;

    @FXML
    private TableColumn<OrdersHistory, Double> TotalCost_col;

    @FXML
    private ComboBox<Integer> ID_CB;

    @FXML
    private ImageView Search_img;

    @FXML
    private TextField Product_TF;

    @FXML
    private ImageView Update_img;

    @FXML
    private Button Search_btn;

    @FXML
    private Button Update_btn;

    @FXML
    private ImageView Excel_img;

    @FXML
    private Text Order_text;

    @FXML
    private Label Sum_label;

    @FXML
    private Text AllOrder_text;

    @FXML
    private Text SumPeriod_text;

    @FXML
    private DatePicker Date_start_DP;

    @FXML
    private DatePicker Date_finish_DP;

    @FXML
    private Text Product_text;

    @FXML
    private Button Excel_btn;


    private int ID;
    private DataAccessObject dao;
    private String query;
    private Date date_start;
    private Date date_finish;
    private String product;


    @FXML
    void initialize() {
        dao = new DataAccessObject();


        Excel_btn.setGraphic(Excel_img);
        Excel_btn.setOnAction(event -> {
            if (ID_CB.getSelectionModel().isEmpty() && Product_TF.getText().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null) {
                getReportAll();
            }else if(Product_TF.getText().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null){
                getReportByID();
            }else if (ID_CB.getSelectionModel().isEmpty() && Date_start_DP.getValue() == null && Date_finish_DP.getValue() == null){
                getReportByProduct();
            }else if (ID_CB.getSelectionModel().isEmpty() && Product_TF.getText().isEmpty()){
                getReportByPeriod();
            }
        });

        Search_btn.setGraphic(Search_img);
        Search_btn.setOnAction(event -> {
            if(Date_start_DP.getValue() != null || Date_finish_DP.getValue() != null){
                SearchByDatePeriod();
                SumByPeriod();
            }else if (Product_TF.getText().isEmpty()){
                SearchByID();
                OrderToLabel();
            }else {
                SearchByProduct();
                SumByProduct();
            }
        });

       Update_btn.setGraphic(Update_img);
       Update_btn.setOnAction(event -> {
           refreshOrderHistoryTable();
           SumPeriod_text.setVisible(false);
           Order_text.setVisible(false);
           Product_text.setVisible(false);
           AllOrder_text.setVisible(true);
           AllOrderToLabel();
           Date_start_DP.setValue(null);
           Date_finish_DP.setValue(null);
           Product_TF.clear();
           ID_CB.getSelectionModel().clearSelection();
       });

        refreshOrderHistoryTable();
        initComboBox();
       AllOrderToLabel();
    }

    private void initOrderHistoryTable(){
        ID_order_col.setCellValueFactory(cellData -> cellData.getValue().getOrder_id().asObject());
        Product_col.setCellValueFactory(cellData -> cellData.getValue().getProduct_order());
        Catogory_col.setCellValueFactory(cellData -> cellData.getValue().getCategory_order());
        Price_col.setCellValueFactory(cellData -> cellData.getValue().getPrice_order().asObject());
        Amount_col.setCellValueFactory(cellData -> cellData.getValue().getAmount_order().asObject());
        Client_col.setCellValueFactory(cellData -> cellData.getValue().getClient_order());
        Date_col.setCellValueFactory((cellData) -> cellData.getValue().getDate_order());
        Discount_col.setCellValueFactory(cellData -> cellData.getValue().getDiscount_order().asObject());
        TotalCost_col.setCellValueFactory(cellData -> cellData.getValue().getTotalcost_order().asObject());
    }


    private void getReportAll(){
            query = "SELECT * FROM sendings_hystory_view";
            dao.getAllOrders(query,  "Все заказы ");
    }

    private void getReportByID(){
        query = "SELECT * FROM sendings_hystory_view WHERE id = '" + ID + "' ";
        dao.getAllOrders(query, "Заказ №" + ID + " " );
    }

    private void getReportByPeriod(){
        query = "SELECT * FROM sendings_hystory_view WHERE date_sending BETWEEN '" + date_start + "' AND '" + date_finish + "'";
        dao.getAllOrders(query, "Заказы за период " + date_start + " - " + date_finish+ " ");
    }

    private void getReportByProduct(){
        query = "SELECT * FROM sendings_hystory_view WHERE name_product = '" + product + "' ";
        dao.getAllOrders(query, "Продажи по товару " + product + " ");

    }

    private void AllOrderToLabel(){

        query = "Select Sum((sending_details.price_by_unit * sending_details.amount) - ((sending_details.price_by_unit * sending_details.amount) * sending.discount / 100)) as TotalCost\n" +
                "                from products join sending_details on products.id = sending_details.id_product join \n" +
                "                sending on sending.id = sending_details.id_sending;";
        Sum_label.setText(String.valueOf(dao.getAllOrderSum(query)));
    }
    private void OrderToLabel(){
        ID = ID_CB.getSelectionModel().getSelectedItem();
        query = "Select Sum((sending_details.price_by_unit * sending_details.amount) - ((sending_details.price_by_unit * sending_details.amount) * sending.discount / 100)) as TotalCost\n" +
                "                from products join sending_details on products.id = sending_details.id_product join \n" +
                "                sending on sending.id = sending_details.id_sending\n" +
                "                where sending.id = '" + ID + "' ";

        AllOrder_text.setVisible(false);
        SumPeriod_text.setVisible(false);
        Order_text.setVisible(true);
        Sum_label.setText(String.valueOf(dao.getAllOrderSum(query)));

    }

    private void SumByPeriod(){
        date_start = Date.valueOf(Date_start_DP.getValue());
        date_finish = Date.valueOf(Date_finish_DP.getValue());
        query = "Select Sum((sending_details.price_by_unit * sending_details.amount) - ((sending_details.price_by_unit * sending_details.amount) * sending.discount / 100)) as TotalCost\n" +
                "                from products join sending_details on products.id = sending_details.id_product join \n" +
                "                sending on sending.id = sending_details.id_sending\n" +
                "                where date_sending BETWEEN '"+ date_start+ "' AND '" + date_finish + "'  ";
        AllOrder_text.setVisible(false);
        Order_text.setVisible(false);
        SumPeriod_text.setVisible(true);
        Sum_label.setText(String.valueOf(dao.getAllOrderSum(query)));
    }

    private void SumByProduct(){
        product = Product_TF.getText();
        query = "Select Sum((sending_details.price_by_unit * sending_details.amount) - ((sending_details.price_by_unit * sending_details.amount) * sending.discount / 100)) as TotalCost\n" +
                "                from products join sending_details on products.id = sending_details.id_product join \n" +
                "                sending on sending.id = sending_details.id_sending\n" +
                "                where name_product = '" + product + "' ";
        AllOrder_text.setVisible(false);
        Order_text.setVisible(false);
        SumPeriod_text.setVisible(false);
        Product_text.setVisible(true);
        Sum_label.setText(String.valueOf(dao.getAllOrderSum(query)));
    }





    private void refreshOrderHistoryTable(){
        initOrderHistoryTable();
        query = "SELECT * FROM sendings_hystory_view";
        OrderH_TV.setItems(dao.getOrdersHistoryData(query));

    }

    private void SearchByID(){
        ID = ID_CB.getSelectionModel().getSelectedItem();
        initOrderHistoryTable();
        query = "SELECT * FROM sendings_hystory_view where id = '" + ID + "' ";
        OrderH_TV.setItems(dao.getOrdersHistoryData(query));

    }

    private void SearchByDatePeriod(){
        date_start = Date.valueOf(Date_start_DP.getValue());
        date_finish = Date.valueOf(Date_finish_DP.getValue());
        query = "SELECT * FROM sendings_hystory_view WHERE  date_sending BETWEEN '" + date_start + "' AND '" + date_finish + "' ";
        OrderH_TV.setItems(dao.getOrdersHistoryData(query));
    }

    private void SearchByProduct(){
        product = Product_TF.getText();
        query = "SELECT * FROM sendings_hystory_view WHERE name_product = '" + product + "'  ";
        OrderH_TV.setItems(dao.getOrdersHistoryData(query));
    }


    private void initComboBox(){
        ID_CB.getSelectionModel().clearSelection();
        query = "SELECT id FROM sendings_hystory_view GROUP BY id ORDER BY id";
        ID_CB.setItems(dao.getOHComboBox(query));
    }

}
