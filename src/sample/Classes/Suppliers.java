package sample.Classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Suppliers {

    private final StringProperty name_sup;
    private final StringProperty adress_sup;
    private final StringProperty telephone_sup;
    private final IntegerProperty id_sup;

    public Suppliers(String name_sup, String adress_sup, String telephone_sup, Integer id_sup){
        this.name_sup = new SimpleStringProperty(name_sup);
        this.adress_sup = new SimpleStringProperty(adress_sup);
        this.telephone_sup = new SimpleStringProperty(telephone_sup);
        this.id_sup = new SimpleIntegerProperty(id_sup);

    }

    public StringProperty getName_sup(){return this.name_sup;}
    public StringProperty getAdress_sup(){return this.adress_sup;}
    public StringProperty getTelephone_sup(){return this.telephone_sup;}
    public IntegerProperty getId_sup(){return this.id_sup;}


}
