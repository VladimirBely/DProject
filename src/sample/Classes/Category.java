package sample.Classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {

    private final StringProperty Product_category;
    private final IntegerProperty id;

    public Category(String Product_category, Integer id) {
        this.Product_category = new SimpleStringProperty(Product_category);
        this.id = new SimpleIntegerProperty(id);
    }


    /*public String getProduct_category() {
        return Product_category.get();
    }*/

    public StringProperty getProduct_category() {
        return Product_category;
    }

    /*public void setProduct_category(String product_category) {
        this.Product_category.set(product_category);
    }*/

   /* public int getId() {
        return id.get();
    }*/

    public IntegerProperty getId() {
        return id;
    }

    /*public void setId(int id) {
        this.id.set(id);
    }*/
}
