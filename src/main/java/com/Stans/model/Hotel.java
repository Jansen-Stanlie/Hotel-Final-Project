package com.Stans.model;
import java.util.Date;

public class Hotel {
    private String hotel_id;
    private String hotel_name;
    private int suite_room;
    private int deluxe_room;
    private int standard_room;
    private int suite_price;
    private int deluxe_price;
    private int standard_price;
    private Date price_update;
    private Date room_update;
    private String location;
    private String status;
    private String rating;



    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public int getSuite_room() {
        return suite_room;
    }

    public void setSuite_room(int suite_room) {
        this.suite_room = suite_room;
    }

    public int getDeluxe_room() {
        return deluxe_room;
    }

    public void setDeluxe_room(int deluxe_room) {
        this.deluxe_room = deluxe_room;
    }

    public int getStandard_room() {
        return standard_room;
    }

    public void setStandard_room(int standard_room) {
        this.standard_room = standard_room;
    }

    public int getSuite_price() {
        return suite_price;
    }

    public void setSuite_price(int suite_price) {
        this.suite_price = suite_price;
    }

    public int getDeluxe_price() {
        return deluxe_price;
    }

    public void setDeluxe_price(int deluxe_price) {
        this.deluxe_price = deluxe_price;
    }

    public int getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(int standard_price) {
        this.standard_price = standard_price;
    }

    public Date getPrice_update() {
        return price_update;
    }

    public void setPrice_update(Date price_update)  {
        ;
        this.price_update = price_update;
    }

    public Date getRoom_update() {
        return room_update;
    }

    public void setRoom_update(Date room_update) {
        this.room_update = room_update;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id='" + hotel_id + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", suite_room=" + suite_room +
                ", deluxe_room=" + deluxe_room +
                ", standard_room=" + standard_room +
                ", suite_price=" + suite_price +
                ", deluxe_price=" + deluxe_price +
                ", standard_price=" + standard_price +
                ", price_update=" + price_update +
                ", room_update=" + room_update +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }


//    public static void main(String[] args) throws ParseException {
//            String a = dateTime();
//            Hotel ab = new Hotel();
//                Date ac = stringToDate(a);
//        System.out.println(ac);
//    }
//    public static String dateTime(){ // real time
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
//        //System.out.println(dtf.format(now));
//        return dtf.format(now);
//    }
//    public static Date stringToDate(String date) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        simpleDateFormat.setLenient(false);
//        return simpleDateFormat.parse(date);
//    }
}
