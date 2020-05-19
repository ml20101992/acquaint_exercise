package com.mateo.exercise.http.models.requests;

import javax.validation.constraints.NotBlank;

public class HotelCreateRequest {

    @NotBlank(message = "Name Field is required")
    private String name;

    @NotBlank(message = "Address Field is required")
    private String address;

    @NotBlank(message = "Zip Field is Required")
    private String zip;

    @NotBlank(message = "Country Field is required")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public HotelCreateRequest() {
    }

    public HotelCreateRequest(String name, String address, String zip, String country) {
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.country = country;
    }
}
