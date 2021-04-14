package com.matteo.restfulwebservices.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorMessage {

    private String message;
    private LocalDateTime dateTime;

    public ErrorMessage(String message, LocalDateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public ErrorMessage() {
    }
}
