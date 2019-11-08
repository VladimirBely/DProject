package sample.Classes;

import javafx.beans.property.*;

import java.sql.Date;

public class Supplies {

    private final IntegerProperty ID_supply;
    private final StringProperty supplier;
    private final ObjectProperty<Date> date_supply;


    public Supplies(Integer ID_supply, String supplier, Date date_supply) {
        this.ID_supply = new SimpleIntegerProperty(ID_supply);
        this.supplier = new SimpleStringProperty(supplier);
        this.date_supply = new SimpleObjectProperty<>(date_supply);

    }

    public IntegerProperty getID_supply(){return ID_supply;}
    public StringProperty getSupplier(){return supplier;}
    public ObjectProperty<Date> getDate_supply(){return date_supply;}

}
