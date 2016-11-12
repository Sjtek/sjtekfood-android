package nl.sjtek.sjtekfood.models;

import java.io.Serializable;

/**
 * Created by wouter on 12-11-16.
 */

public class Meal implements Serializable {

    private long id;
    private String name;

    public Meal() {
    }

    public Meal(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
