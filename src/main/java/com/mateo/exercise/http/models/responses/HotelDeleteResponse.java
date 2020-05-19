package com.mateo.exercise.http.models.responses;

public class HotelDeleteResponse {
    private String message;

    public HotelDeleteResponse(String message) {
        this.message = message;
    }

    public HotelDeleteResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
