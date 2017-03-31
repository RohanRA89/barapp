package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rohanayub on 3/15/17.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BarInformation {
    @Id
    @GeneratedValue
    private long id;
    private String barName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BarAddress> barAddress;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<BarDrinks> barDrinkInformation;
    private String jsonResult;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public List<BarAddress> getBarAddress() {
        return barAddress;
    }

    public void setBarAddress(List<BarAddress> barAddress) {
        this.barAddress = barAddress;
    }

    public List<BarDrinks> getBarDrinkInformation() {
        return barDrinkInformation;
    }

    public void setBarDrinkInformation(List<BarDrinks> barDrinkInformation) {
        this.barDrinkInformation = barDrinkInformation;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public BarInformation() {

    }

    @Override
    public String toString() {
        return
                "barName: " + barName +
                "\nbarAddress:\n" + barAddress+"\n";

    }
}
