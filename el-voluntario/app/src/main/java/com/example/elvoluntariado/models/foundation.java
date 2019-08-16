package com.example.elvoluntariado.models;



public class foundation {

    private String name;
    private String address;
    private String phoneNumber;
    private String person;
    private double lat;
    private  double lng;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLat() {
        return lat;
    }

    public double setLat(double lat) {
        this.lat = lat;
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double setLng(double lng) {
        this.lng = lng;
        return lng;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

}
