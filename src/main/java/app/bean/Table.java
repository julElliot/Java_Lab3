package app.bean;

import java.util.Objects;

public class Table {
    private int id;
    private boolean isFree;
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Table() {
    }

    public Table(int id) {
        this.id = id;
    }

    public Table(int id, boolean isFree, int number) {
        this(id);

        this.isFree = isFree;
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Table table = (Table) obj;

        return isFree == table.isFree &&
                number == table.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFree, number);
    }

    @Override
    public String toString() {
        return "Table{" +
                "isFree=" + isFree +
                ", number=" + number +
                '}';
    }
}
