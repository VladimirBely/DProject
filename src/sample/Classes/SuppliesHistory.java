package sample.Classes;

import javafx.beans.property.*;

import java.sql.Date;

public class SuppliesHistory {

    private final IntegerProperty ID_Supply;
    private final StringProperty product;
    private final StringProperty category;
    private final IntegerProperty amount;
    private final StringProperty supplier;
    private final ObjectProperty<Date> date_supply;

    public SuppliesHistory(Integer ID_Supply, String product, String category, Integer amount, String supplier, Date date_supply){
        this.ID_Supply = new SimpleIntegerProperty(ID_Supply);
        this.product = new SimpleStringProperty(product);
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleIntegerProperty(amount);
        this.supplier = new SimpleStringProperty(supplier);
        this.date_supply = new SimpleObjectProperty<>(date_supply);
    }

    public IntegerProperty getID_Supply(){return ID_Supply;}
    public StringProperty getProduct(){return product;}
    public StringProperty getCategory(){return category;}
    public IntegerProperty getAmount(){return amount;}
    public StringProperty getSupplier(){return supplier;}
    public ObjectProperty<Date> getDate_supply(){return date_supply;}
}
