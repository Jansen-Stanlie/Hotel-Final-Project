package com.Stans.repository;

import com.Stans.model.Booking;
import com.Stans.service.MyBatisUtil;
import com.Stans.service.MyBatisUtilHotel;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class PaymentCheck extends TimerTask{
   private String username;
    public PaymentCheck(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        Date date = new Date();
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        SqlSession session2 = MyBatisUtilHotel.getSqlSessionFactory().openSession();
        SqlSession session3 = MyBatisUtilHotel.getSqlSessionFactory().openSession();
        Booking booking = new Booking();
        Booking updatebook = new Booking();
        booking = session.selectOne("User.check",username);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("date",date);
        map.put("room",booking.getRoom_type());
        map.put("hotel",booking.getHotel_name());
        updatebook = session2.selectOne("Hotel.datakamar",map);
        booking.setKamarBaru( booking.getQuantity() + updatebook.getKamar_kosong());
        booking.setBooking_date(date);
        session.update("User.updatePemesanan",map);
        session3.update("Hotel.insertUpdateRoom",booking);
        session3.commit();
        session3.close();
        session2.commit();
        session2.close();
        session.commit();
        session.close();


    }
}
