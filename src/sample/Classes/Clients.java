package sample.Classes;

import javafx.beans.property.*;

public class Clients {

    private final StringProperty name;
    private final StringProperty adress;
    private final StringProperty telephone;
    private final DoubleProperty deposit;
    private final DoubleProperty balance;
    private final IntegerProperty id;

    public Clients(String name, String adress, String telephone, Double deposit, Double balance, Integer id){
        this.name = new SimpleStringProperty(name);
        this.adress = new SimpleStringProperty(adress);
        this.telephone = new SimpleStringProperty(telephone);
        this.deposit = new SimpleDoubleProperty(deposit);
        this.balance = new SimpleDoubleProperty(balance);
        this.id = new SimpleIntegerProperty(id);

    }

    public StringProperty getName(){return name;}
    public StringProperty getAdress(){return adress;}
    public StringProperty getTelephone(){return telephone;}
    public DoubleProperty getDeposit(){return deposit;}
    public DoubleProperty getBalance(){return balance;}
    public IntegerProperty getId(){return id;}
}
