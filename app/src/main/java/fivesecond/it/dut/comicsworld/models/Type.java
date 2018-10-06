package fivesecond.it.dut.comicsworld.models;

import java.io.Serializable;

public class Type implements Serializable {
    private int id ;
    private String name ;


    public int getId() {
        return id;
    }

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type() {
    }


    public void setId(int id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
