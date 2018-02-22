package com.example.peter.shoppinglist;

/**
 * Created by Peter on 20/02/2018.
 */

public class Object {
    private int id;
    private String data;

    public Object(int id, String data) {
        this.id = id;
        this.data = data;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
