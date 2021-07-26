package com.repository;

import com.model.Booking;
import com.model.Hotel;
import com.service.MyBatisUtilHotel;
import com.service.MyBatisUtilPemesanan;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;


import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class hotelrepo{
    private String booking_status;
    private String message;
    private Date datepesan;
    private Date datecheckout;

    public List<Hotel> findAll(){
        List<Hotel> hotel = new ArrayList<>();
        try {
            SqlSession session = MyBatisUtilHotel.getSqlSessionFactory().openSession();
            hotel= session.selectList("Hotel.selectAll");
        }catch (Exception e){
            e.printStackTrace();
        }
        return hotel;
    }
    public List<Hotel> findByLocation(String location){
        List<Hotel> hotel = new ArrayList<>();
        try {
            SqlSession session = MyBatisUtilHotel.getSqlSessionFactory().openSession();
            hotel= session.selectList("Hotel.getByLocation",location);
            session.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return hotel;
    }
    public List<Hotel> findByRatings(String ratings){
        List<Hotel> hotel = new ArrayList<>();
        try {
            SqlSession session = MyBatisUtilHotel.getSqlSessionFactory().openSession();
            hotel= session.selectList("Hotel.getByRatings",ratings);
            session.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return hotel;
    }
    public List<Hotel> findByRoom(String room,String location){
        List<Hotel> hotel = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("room",room);
        map.put("location",location);
        try {
            SqlSession session = MyBatisUtilHotel.getSqlSessionFactory().openSession();
            hotel= session.selectList("Hotel.getByRoomType",map);
            session.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return hotel;
    }
    public Booking bookingHotel(JSONObject jobj) throws ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jobj.toString());
        JSONObject temp = (JSONObject) obj;
        JSONObject temp1 = (JSONObject) obj;
        Booking booking = new Booking();

        for(Iterator iterator = temp1.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            booking.setUsername(String.valueOf(temp.get("username")));
            if (temp1.get(key) instanceof JSONObject) {
                JSONObject temp2 = (JSONObject) temp1.get(key);
                booking = new Booking(String.valueOf(temp2.get("name")), String.valueOf(temp2.get("hotel name")));
                JSONObject temp3 = (JSONObject) temp2.get("Room");
                booking.setRoom_type(String.valueOf(temp3.get("Type")));
                booking.setQuantity(Integer.parseInt(String.valueOf(temp3.get("Quantity"))));
                booking.setDays(Integer.parseInt(String.valueOf(temp3.get("Nights"))));
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(temp3.get("CheckIn Date")));
                booking.setBooking_date(date1);
            }
        }
        return booking;
    }
    public JSONObject bookDetail(JSONObject jobj) throws ParseException, java.text.ParseException {
        JSONObject obj = new JSONObject();
        datepesan = new Date();

        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();

        SqlSession session = MyBatisUtilPemesanan.getSqlSessionFactory().openSession();
        SqlSession session3 = MyBatisUtilPemesanan.getSqlSessionFactory().openSession();
        SqlSession session2 = MyBatisUtilHotel.getSqlSessionFactory().openSession();
        Booking bookdetail = bookingHotel(jobj);
        String roomName = bookdetail.getRoom_type();
        String hotelName = bookdetail.getHotel_name();
        String booking_id = getAlphaNumericString(8);
        String Username = bookdetail.getUsername();
        datecheckout = bookdetail.getBooking_date();


        Booking databasecheck = SelectData(roomName,hotelName);
        Booking checkPaymentStat = checkBook(Username);
        if (databasecheck.getKamar_kosong() == 0) {
            booking_status = "Failed";
            message ="Room Full,please book another hotel or another available room";
            obj.put("Message",message);
            obj.put("Booking Status",booking_status);
        }else{
              if(checkPaymentStat == null){
                  if(databasecheck.getKamar_kosong() < bookdetail.getQuantity()) {
                      booking_status = "Failed";
                      message = "Room Requested more than Available room,please book another hotel or another available room";
                      obj.put("Message", message);
                      obj.put("Booking Status", booking_status);
                    }else if(bookdetail.getQuantity() == 0){
                      booking_status = "Failed";
                      message = "Requested room cannot be empty";
                      obj.put("Message", message);
                      obj.put("Booking Status", booking_status);
                    }else if(bookdetail.getDays() > 31){
                      booking_status = "Failed";
                      message = "Booking nights cannot more than 31 Days";
                      obj.put("Message", message);
                      obj.put("Booking Status", booking_status);
                  }else{
                        booking_status = "Success";
                        message ="Please Complete your payment using your booking id";
                        bookdetail.setBooking_id(booking_id);
                        bookdetail.setToday(datepesan);
                        Date pesan = new Date();
                        pesan = bookdetail.getBooking_date();
                        cal1.setTime(pesan);
                        cal1.add(Calendar.HOUR_OF_DAY,14);
                        bookdetail.setBooking_date(cal1.getTime());
                        cal.setTime(datecheckout);
                        cal.add(Calendar.DAY_OF_MONTH,bookdetail.getDays());
                        cal.add(Calendar.HOUR_OF_DAY,12);
                        datecheckout = cal.getTime();
                        bookdetail.setCheckout_date(datecheckout);
                        bookdetail.setKamarBaru(jumlahKamarNew(bookdetail.getQuantity(),databasecheck.getKamar_kosong()));
                        bookdetail.setTotal_payment(databasecheck.getHarga_kamar()*bookdetail.getQuantity()* bookdetail.getDays());
                        obj.put("Booking Status", booking_status);
                        JSONObject obj2 = new JSONObject();
                        obj2.put("name",bookdetail.getName());
                        obj2.put("Hotel Name",bookdetail.getHotel_name());
                        JSONObject obj3 = new JSONObject();
                        obj3.put("Room type",bookdetail.getRoom_type());
                        obj3.put("Quantity",bookdetail.getQuantity());
                        obj2.put("Booking id",bookdetail.getBooking_id());
                        obj2.put("Checkin Date",bookdetail.getBooking_date().toString());
                        obj2.put("Booking Date",bookdetail.getToday().toString());
                        obj2.put("Checkout Date",bookdetail.getCheckout_date().toString());
                        obj2.put("Room Price",databasecheck.getHarga_kamar());
                        obj2.put("Total Payment",bookdetail.getTotal_payment());
                        obj2.put("number of nights",bookdetail.getDays());
                        obj2.put("Room", obj3);
                        obj.put("Booking Details", obj2);
                        obj.put("Message",message);
                        session.insert("Pemesanan.insert", bookdetail);
                        session2.update("Hotel.insertUpdateRoom",bookdetail);
                        session2.commit();
                        session2.close();
                        session.commit();
                        session.close();
                  }
              }else if(checkPaymentStat.getPayment_status().equalsIgnoreCase("pending") && checkPaymentStat != null){
                    booking_status = "failed";
                    message = "Can not make a new Booking,"
                            +"Payment Still "+checkPaymentStat.getPayment_status()+" Please Complete Payment for " + bookdetail.getUsername()
                            + " with booking id "+ checkPaymentStat.getBooking_id();
                    obj.put("Message",message);
                    obj.put("Booking Status", booking_status);

                }else{
                  if(databasecheck.getKamar_kosong() < bookdetail.getQuantity()){
                      booking_status = "Failed";
                      message ="Room Requested more than Available room,please book another hotel or another available room";
                      obj.put("Message",message);
                      obj.put("Booking Status",booking_status);
                  }else if(bookdetail.getQuantity() == 0){
                      booking_status = "Failed";
                      message = "Requested room cannot be empty";
                      obj.put("Message", message);
                      obj.put("Booking Status", booking_status);
                  }else if(bookdetail.getDays() > 31){
                      booking_status = "Failed";
                      message = "Booking nights cannot more than 31 Days";
                      obj.put("Message", message);
                      obj.put("Booking Status", booking_status);
                  }else{
                      booking_status = "Success";
                      message ="Please Complete your payment using your booking id";
                      bookdetail.setBooking_id(booking_id);
                      bookdetail.setToday(datepesan);
                      Date pesan = new Date();
                      pesan = bookdetail.getBooking_date();
                      cal1.setTime(pesan);
                      cal1.add(Calendar.HOUR_OF_DAY,14);
                      bookdetail.setBooking_date(cal1.getTime());
                      cal.setTime(datecheckout);
                      cal.add(Calendar.DAY_OF_MONTH,bookdetail.getDays());
                      cal.add(Calendar.HOUR_OF_DAY,12);
                      datecheckout = cal.getTime();
                      bookdetail.setCheckout_date(datecheckout);
                      session.delete("Pemesanan.delete", bookdetail);
                      session.commit();
                      session.close();
                      bookdetail.setKamarBaru(jumlahKamarNew(bookdetail.getQuantity(),databasecheck.getKamar_kosong()));
                      bookdetail.setTotal_payment(databasecheck.getHarga_kamar()*bookdetail.getQuantity()* bookdetail.getDays());
                      obj.put("Booking Status", booking_status);
                      JSONObject obj2 = new JSONObject();
                      obj2.put("name",bookdetail.getName());
                      obj2.put("Hotel Name",bookdetail.getHotel_name());
                      JSONObject obj3 = new JSONObject();
                      obj3.put("Room type",bookdetail.getRoom_type());
                      obj3.put("Quantity",bookdetail.getQuantity());
                      obj2.put("Booking id",bookdetail.getBooking_id());
                      obj2.put("Checkin Date",bookdetail.getBooking_date().toString());
                      obj2.put("Booking Date",bookdetail.getToday().toString());
                      obj2.put("Checkout Date",bookdetail.getCheckout_date().toString());
                      obj2.put("Room Price",databasecheck.getHarga_kamar());
                      obj2.put("Total Payment",bookdetail.getTotal_payment());
                      obj2.put("number of nights",bookdetail.getDays());
                      obj2.put("Room", obj3);
                      obj.put("Booking Details", obj2);
                      obj.put("Message",message);

                      session2.update("Hotel.insertUpdateRoom",bookdetail);
                      session3.insert("Pemesanan.replace",bookdetail);
                      session3.commit();
                      session3.close();
                      session2.commit();
                      session2.close();

                     }
              }

        }

        return obj;
    }
    public String getAlphaNumericString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
    public Booking SelectData(String tipe_kamar,String hotel){
        Booking checkbook = new Booking();
        Map<String,String> map = new HashMap<>();
        map.put("room",tipe_kamar);
        map.put("hotel",hotel);
        SqlSession session = MyBatisUtilHotel.getSqlSessionFactory().openSession();
        checkbook = session.selectOne("Hotel.datakamar",map);
        session.commit();
        session.close();
        return checkbook;
    }
    public Booking checkBook(String username){
        Booking checkBook = new Booking();
        SqlSession session = MyBatisUtilPemesanan.getSqlSessionFactory().openSession();
        checkBook = session.selectOne("Pemesanan.check",username);
        return checkBook;
    }
    public int jumlahKamarNew(int kamar_pesanan, int kamar_database){
        return kamar_database - kamar_pesanan;
    }
}
