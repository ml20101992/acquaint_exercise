package com.mateo.exercise.http.models.responses;

import com.mateo.exercise.data.models.HotelModel;

public class SingleHotelResponse {
    private HotelModel data;
    private String action;

    public HotelModel getData() {
        return data;
    }

    public void setData(HotelModel data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public SingleHotelResponse(HotelModel data, String action) {
        this.data = data;
        this.action = action;
    }

    public SingleHotelResponse(HotelModel data) {
        this.data = data;
    }
}
