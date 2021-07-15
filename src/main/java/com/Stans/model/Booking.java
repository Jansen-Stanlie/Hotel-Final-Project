package com.Stans.model;

import java.util.Date;

public class Booking {
    private int id;
    private String name;
    private String username;
    private String hotel_name;
    private String Booking_id;
    private String Room_type;
    private String location;
    private long harga_kamar;
    private int kamar_kosong;
    private int Quantity;
    private String payment_status = "Pending";
    private long Total_payment;
    private Date booking_date;
    private Date checkout_date;
    private int kamarBaru;
    private int Days;
    private Date today;
    public Booking(String fullname, String hotel_name) {
        this.name = fullname;
        this.hotel_name = hotel_name;

    }

    public Booking() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getBooking_id() {
        return Booking_id;
    }

    public void setBooking_id(String booking_id) {
        Booking_id = booking_id;
    }

    public String getRoom_type() {
        return Room_type;
    }

    public void setRoom_type(String room_type) {
        Room_type = room_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getHarga_kamar() {
        return harga_kamar;
    }

    public void setHarga_kamar(long harga_kamar) {
        this.harga_kamar = harga_kamar;
    }

    public int getKamar_kosong() {
        return kamar_kosong;
    }

    public void setKamar_kosong(int kamar_kosong) {
        this.kamar_kosong = kamar_kosong;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }


    public long getTotal_payment() {
        return Total_payment;
    }

    public void setTotal_payment(long total_payment) {
        Total_payment = total_payment;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public int getKamarBaru() {
        return kamarBaru;
    }

    public void setKamarBaru(int kamarBaru) {
        this.kamarBaru = kamarBaru;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public Date getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Date checkout_date) {
        this.checkout_date = checkout_date;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", Booking_id='" + Booking_id + '\'' +
                ", Room_type='" + Room_type + '\'' +
                ", location='" + location + '\'' +
                ", harga_kamar=" + harga_kamar +
                ", kamar_kosong=" + kamar_kosong +
                ", Quantity=" + Quantity +
                ", payment_status='" + payment_status + '\'' +
                ", Total_payment=" + Total_payment +
                ", booking_date=" + booking_date +
                ", checkout_date=" + checkout_date +
                ", kamarBaru=" + kamarBaru +
                ", Days=" + Days +
                ", today=" + today +
                '}';
    }
}