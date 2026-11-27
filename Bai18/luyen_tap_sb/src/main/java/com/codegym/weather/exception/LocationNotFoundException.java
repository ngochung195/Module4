package com.codegym.weather.exception;

public class LocationNotFoundException extends RuntimeException {

    private final String location;

    public LocationNotFoundException(String location) {
        super("Không tìm thấy dữ liệu thời tiết cho địa phương: " + location);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
