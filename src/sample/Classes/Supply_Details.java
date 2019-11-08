package sample.Classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Supply_Details {

    private final StringProperty product_sup;
    private final IntegerProperty id_sup;
    private final IntegerProperty amount_sup;
    private final IntegerProperty SupID;

    public Supply_Details(String product_sup, Integer id_sup, Integer amount_sup, Integer SupID) {
        this.product_sup = new SimpleStringProperty(product_sup);
        this.id_sup = new SimpleIntegerProperty(id_sup);
        this.amount_sup = new SimpleIntegerProperty(amount_sup);
        this.SupID = new SimpleIntegerProperty(SupID);

    }

    public StringProperty getProduct_sup(){return product_sup;}
    public IntegerProperty getId_sup(){return id_sup;}
    public IntegerProperty getAmount_sup(){return amount_sup;}
    public IntegerProperty getSupID(){return SupID;}
}
