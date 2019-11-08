package sample.Classes;

import javafx.beans.property.*;

import java.sql.Date;

public class OrdersHistory {

    private final IntegerProperty order_id;
    private final StringProperty product_order;
    private final StringProperty category_order;
    private final DoubleProperty price_order;
    private final IntegerProperty amount_order;
    private final StringProperty client_order;
    private final ObjectProperty<Date> date_order;
    private final DoubleProperty discount_order;
    private final DoubleProperty totalcost_order;

    public OrdersHistory(Integer order_id, String product_order, String category_order, Double price_order, Integer amount_order, String client_order, Date date_order, Double discount_order, Double totalcost_order) {
        this.order_id = new SimpleIntegerProperty(order_id);
        this.product_order = new SimpleStringProperty(product_order);
        this.category_order = new SimpleStringProperty(category_order);
        this.price_order = new SimpleDoubleProperty(price_order);
        this.amount_order = new SimpleIntegerProperty(amount_order);
        this.client_order = new SimpleStringProperty(client_order);
        this.date_order = new SimpleObjectProperty<>(date_order);
        this.discount_order = new SimpleDoubleProperty(discount_order);
        this.totalcost_order = new SimpleDoubleProperty(totalcost_order);
    }

    public IntegerProperty getOrder_id(){return order_id;}
    public StringProperty getProduct_order(){return product_order;}
    public StringProperty getCategory_order(){return category_order;}
    public DoubleProperty getPrice_order(){return price_order;}
    public IntegerProperty getAmount_order(){return amount_order;}
    public StringProperty getClient_order(){return client_order;}
    public ObjectProperty<Date> getDate_order(){return date_order;}
    public DoubleProperty getDiscount_order(){return discount_order;}
    public DoubleProperty getTotalcost_order(){return totalcost_order;}
}
