package com.mateo.exercise.http.models.responses;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class HotelNotFoundResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HotelNotFoundResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }
}
