package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Classes.Products;
import sample.DB.DataAccessObject;

import java.io.IOException;


public class Controller {

    @FXML
    private Button AllProducts;

    @FXML
    private Button Sound;

    @FXML
    private Button Light;

    @FXML
    private Button Laser;

    @FXML
    private Button DynamicHead;

    @FXML
    private Button Wires;

    @FXML
    private Button Accessories;

    @FXML
    private Button Cases;

    @FXML
    private Button Video;

    @FXML
    private TextField Name_TF;

    @FXML
    private TextField Price_TF;

    @FXML
    private TextField Amount_product_TF;

    @FXML
    private ComboBox<String> Category_CB;

    @FXML
    private TextArea Char_TA;

    @FXML
    private Button Add_btn;

    @FXML
    private Button Edit_btn;

    @FXML
    private Button Delete_btn;

    @FXML
    private Button Search_button;

    @FXML
    private ImageView Search_img;

    @FXML
    private TableView<Products> Product_TV;

    @FXML
    private TableColumn<Products, String> Name_col;

    @FXML
    private TableColumn<Products, Integer> column_ID;

    @FXML
    private TableColumn<Products, String> Сharacteristics_col;

    @FXML
    private TableColumn<Products, Double> Price_col;

    @FXML
    private TableColumn<Products, Integer> Amount_col;

    @FXML
    private TableColumn<Products, String> Category_col;


    @FXML
    void ShowSuppliers(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/Suppliers.fxml"));
            stage.setTitle("Поставщики");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void showClients(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/Clients.fxml"));
            stage.setTitle("Заказчики");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showOrderRegistration(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/OrderForm.fxml"));
            stage.setTitle("Оформление заказа");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void showSupplyRegistration(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/SupplyRegistration.fxml"));
            stage.setTitle("Оформление прихода");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showSupplyHistory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/SuppliesHistory.fxml"));
            stage.setTitle("История поставок");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void showOrderHistory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/OrderHistory.fxml"));
            stage.setTitle("История заказов");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String name;
    private String characteristics;
    private Double price;
    private Integer amount;
    private String category;
    private DataAccessObject dao;
    private String query;
    private boolean EDIT = false;
    private boolean ADD = true;
    private int ID;

    @FXML
    void initialize() {

        dao = new DataAccessObject();
        Category_CB.setOnMouseClicked((e) -> initCategory());

        Add_btn.setOnAction(event -> {
            if(!validateFields()){
                return;
            }
           saveProducts();
        });

        Edit_btn.setOnAction((e) -> {
            if (!editIsSelected()){
                return;
            }
            ADD = false;
            EDIT = true;
            editProducts();
           });

        Delete_btn.setOnAction((e) -> {
           if (!deleteIsSelected()){
               return;
           }
            deleteProduct();
        });

        Search_button.setGraphic(Search_img);
        Search_button.setOnAction(event -> {
            searchProduct();
        });

        refreshProductTable();

        AllProducts.setOnAction(event -> refreshProductTable());
        Sound.setOnAction(event -> showSoundEquipment());
        Light.setOnAction(event -> showLightEquipment());
        Laser.setOnAction(event -> showLaserSystems());
        DynamicHead.setOnAction(event -> showDynamicHeads());
        Wires.setOnAction(event -> showWires());
        Accessories.setOnAction(event -> showAC());
        Cases.setOnAction(event -> showCases());
        Video.setOnAction(event -> showVideo());

    }

    private boolean editIsSelected(){
        if (Product_TV.getSelectionModel().isEmpty()){
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите редактировать!");
           return false;
        }
        return true;
    }

    private boolean deleteIsSelected(){
        if (Product_TV.getSelectionModel().isEmpty()) {
            DialogController.showWarningDialog("Предупреждение", "Выберите запись, которую хотите удалить!");
            return false;
        }
        return true;
    }

    private boolean validateFields(){
        if (Name_TF.getText() == null || Char_TA.getText() == null || Price_TF.getText() == null || Amount_product_TF.getText() == null || Category_CB.getSelectionModel() == null){
            DialogController.showWarningDialog("Предупреждение", "Для добавления нового товара все поля должны быть заполнены!");
            return false;
        }
        return true;
    }

    private void saveProducts() {
        name = Name_TF.getText();
        characteristics = Char_TA.getText();
        price = Double.valueOf(Price_TF.getText());
        amount = Integer.valueOf(Amount_product_TF.getText());
        category = Category_CB.getSelectionModel().getSelectedItem();

        if (EDIT) {
            query = "UPDATE Products SET name_product = '" + name + "', characteristic = '" + characteristics + "',  price = '" + price + "', amount='" + amount + "', " +
                    "id_category = (Select Categories.id from Categories where name_category = '" + category + "') WHERE products.id = '" + ID + "'";

        } else if (ADD) {
            query = "INSERT INTO products(name_product, characteristic, price, amount, id_category)" +
                    " VALUES('" + name + "', '" + characteristics + "', '" + price + "', '" + amount + "', (Select Categories.id from Categories where name_category = '" + category + "'))";
        }

        dao.saveData(query);
        Name_TF.clear();
        Char_TA.clear();
        Price_TF.clear();
        Amount_product_TF.clear();
        Category_CB.getSelectionModel().clearSelection();
        refreshProductTable();
        ADD = true;
        EDIT = false;
    }

     private void initProductTable() {
        Name_col.setCellValueFactory(cell ->  cell.getValue().getName());
        Сharacteristics_col.setCellValueFactory(cell -> cell.getValue().getCharacteristics());
        Price_col.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        Amount_col.setCellValueFactory(cellData -> cellData.getValue().getAmount().asObject());
        Category_col.setCellValueFactory(cellData -> cellData.getValue().getCategory());
        column_ID.setCellValueFactory(cellData -> cellData.getValue().getId_product().asObject());
    }


    private void refreshProductTable() {
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products LEFT JOIN categories  ON categories.id = id_category ORDER BY Categories.id";
        Product_TV.setItems(dao.getProductsData(query));
    }


    private void initCategory() {
        Category_CB.getSelectionModel().clearSelection();
        query = "SELECT name_category FROM categories ORDER BY Categories.id";
        Category_CB.setItems(dao.getCategoryComboBox(query));
    }

    private void searchProduct(){
        name = Name_TF.getText();
        category = Category_CB.getSelectionModel().getSelectedItem();
        query = "SELECT name_product, characteristic, price, amount, name_category, Products.id FROM Products JOIN Categories ON id_category = Categories.id " +
                "WHERE name_product like '%" + name + "%'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void deleteProduct() {
        Products selected = Product_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId_product().get();
        query = "DELETE FROM products WHERE products.id =" + ID;
        dao.saveData(query);
        refreshProductTable();
    }

    private void editProducts() {
        Products selected = Product_TV.getSelectionModel().getSelectedItem();
        ID = selected.getId_product().get();
        Name_TF.setText(selected.getName().get());
        Char_TA.setText(selected.getCharacteristics().get());
        Price_TF.setText(String.valueOf((Double)selected.getPrice().get()));
        Amount_product_TF.setText(String.valueOf((Integer)selected.getAmount().get()));
        Category_CB.getSelectionModel().select(String.valueOf(selected.getCategory().get()));
    }


    private void showSoundEquipment(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Звуковое оборудование' ";
        Product_TV.setItems(dao.getProductsData(query));

    }

    private void showLightEquipment(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Световое оборудование'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showLaserSystems(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Лазерные системы'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showDynamicHeads(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Динамические головки'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showWires(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Кабеля, шнуры, переходники'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showAC(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Комплектующие для АС'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showCases(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Фурнитура для кейсов'";
        Product_TV.setItems(dao.getProductsData(query));
    }

    private void showVideo(){
        initProductTable();
        query = "SELECT name_product, characteristic, price, amount, categories.name_category, products.id FROM Products  JOIN categories  ON categories.id = id_category WHERE name_category = 'Видеопроекционное оборудование'";
        Product_TV.setItems(dao.getProductsData(query));
    }



}
