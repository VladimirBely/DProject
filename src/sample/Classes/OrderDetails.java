package sample.Classes;

import javafx.beans.property.*;

public class OrderDetails {

    private final StringProperty product;
    private final IntegerProperty id_send;
    private final IntegerProperty amount;
    private final DoubleProperty price;
    private final IntegerProperty SDID;

    public OrderDetails(String product, Integer id_send, Integer amount, Double price, Integer SDID) {
        this.product = new SimpleStringProperty(product);
        this.id_send = new SimpleIntegerProperty(id_send);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleDoubleProperty(price);
        this.SDID = new SimpleIntegerProperty(SDID);

    }

    public StringProperty getProduct(){return product;}
    public IntegerProperty getId_send(){return id_send;}
    public IntegerProperty getAmount(){return amount;}
    public DoubleProperty getPrice(){return price;}
    public IntegerProperty getSDID(){return SDID;}

}
