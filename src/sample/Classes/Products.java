package sample.Classes;

import javafx.beans.property.*;


public class Products {

    private StringProperty name;
    private StringProperty characteristics;
    private DoubleProperty price;
    private IntegerProperty amount;
    private StringProperty category;
    private IntegerProperty id_product;


    public Products(String name, String characteristics, Double price, Integer amount, String category, Integer id_product) {
        this.name = new SimpleStringProperty(name);
        this.characteristics = new SimpleStringProperty(characteristics);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.id_product = new SimpleIntegerProperty(id_product);
    }



    public StringProperty getName() {
        return name;
    }

    public StringProperty getCharacteristics() {
        return characteristics;
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public IntegerProperty getAmount() {
        return amount;
    }

    public StringProperty getCategory() {
        return category;
    }

    public IntegerProperty getId_product() {
        return id_product;
    }

}