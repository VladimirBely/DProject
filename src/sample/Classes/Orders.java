package sample.Classes;

import javafx.beans.property.*;

import java.sql.Date;


public class Orders {

    private final IntegerProperty SID;
    private final StringProperty client;
    private final ObjectProperty<Date> date;
    private final DoubleProperty discount;
    private final DoubleProperty totalcost;

    public Orders(Integer SID, String client, Date date, Double discount, Double totalcost ){
        this.SID = new SimpleIntegerProperty(SID);
        this.client = new SimpleStringProperty(client);
        this.date = new SimpleObjectProperty<>(date);
        this.discount = new SimpleDoubleProperty(discount);
        this.totalcost = new SimpleDoubleProperty(totalcost);

    }

    public IntegerProperty getSID() { return SID; }
    public StringProperty getClient(){return client;}
    public ObjectProperty<Date> getDate() { return date;}
    public DoubleProperty getDiscount(){return discount;}
    public DoubleProperty getTotalcost(){return totalcost;}

}
