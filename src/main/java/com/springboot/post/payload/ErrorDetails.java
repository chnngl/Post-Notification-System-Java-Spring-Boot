package com.springboot.post.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    public String details ;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
