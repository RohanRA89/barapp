package com.ironyard.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rohanayub on 3/15/17.
 */
@Entity
public class BarAddress {
    @Id
    @GeneratedValue
    private long id;
    private String streetAddress;
    private String cityName;
    private String stateName;
    private Integer zipcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public BarAddress() {
    }

    @Override
    public String toString() {
        return streetAddress+"\n"+cityName+"\n"+stateName+"\n"+zipcode;


    }
}
