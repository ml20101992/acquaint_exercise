package com.mateo.exercise.data.models;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "hotels")
public class HotelModel implements Serializable {
    private final static long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String hotelName;

    @Column(name = "address")
    private String address;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;

    public HotelModel() {
    }

    public HotelModel(long id, String hotelName, String address, String zip, String country) {
        this.id = id;
        this.hotelName = hotelName;
        this.address = address;
        this.zip = zip;
        this.country = country;
    }

    public HotelModel(String hotelName, String address, String zip, String country) {
        this.hotelName = hotelName;
        this.address = address;
        this.zip = zip;
        this.country = country;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
