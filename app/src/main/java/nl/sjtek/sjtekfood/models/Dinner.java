package nl.sjtek.sjtekfood.models;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wouter on 12-11-16.
 */

public class Dinner implements Serializable {

    private long id;
    private Date date;
    private Meal meal;

    public Dinner() {
    }

    public Dinner(Date date, Meal meal) {
        this.date = date;
        this.meal = meal;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Meal getMeal() {
        return meal;
    }


}
