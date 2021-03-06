package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rohanayub on 3/17/17.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarDrinks {
    @Id
    @GeneratedValue
    private Long id;

    private String mixerName;
    private String beverageType;
    private String alcoholName;
    private String jsonResult;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMixerName() {
        return mixerName;
    }

    public void setMixerName(String mixerName) {
        this.mixerName = mixerName;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(String beverageType) {
        this.beverageType = beverageType;
    }

    public String getAlcoholName() {
        return alcoholName;
    }

    public void setAlcoholName(String alcoholName) {
        this.alcoholName = alcoholName;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public BarDrinks() {
    }

    @Override
    public String toString() {
        return "BarDrinks{" +
                ", mixerName='" + mixerName + '\'' +
                ", beverageType='" + beverageType + '\'' +
                ", alcoholName='" + alcoholName + '\'' +
                '}';
    }
}
