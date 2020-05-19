package com.mateo.exercise.http.models.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class HotelUpdateRequest extends HotelCreateRequest {
    @Min(1)
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HotelUpdateRequest(String name, String address, String zip, String country, @NotNull int id) {
        super(name, address, zip, country);
        this.id = id;
    }
}

