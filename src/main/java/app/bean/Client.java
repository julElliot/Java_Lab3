package app.bean;

import java.util.Objects;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private double money;
    private Table table;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            return;
        }

        this.money = money;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Client() {
    }

    public Client(int id) {
        this.id = id;
    }

    public Client(int id, double money, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        setMoney(money);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }

        Client client = (Client) obj;
        return id == client.id &&
                money == client.money &&
                Objects.equals(table, client.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, money, table);
    }
}
