package com.Stans.repository;


import com.Stans.model.Booking;
import com.Stans.model.User;
import com.Stans.service.MyBatisUtil;
import com.Stans.service.MyBatisUtilHotel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserRepo{
    public static User validateUser(String username, String password) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("password",password);

        User user =(User) session.selectOne("User.userlog",map);
           if(user != null){
               session.commit();
               session.close();
               return user;
           }else{
               session.commit();
               session.close();
               return user;
           }
    }
    public static User validateRegis(String username, String password) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        User user =(User) session.selectOne("User.userregis",username);
        if(user != null){
            session.commit();
            session.close();
            return user;
        }else{
            session.commit();
            session.close();
            return user;
        }
    }
    public void regisUser(User user){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        String username = user.getUsername();
        String password = user.getPassword();

        User user1 = new User(username,password);
        session.insert("User.insert",user1);
        session.commit();
        session.close();
        System.out.println("User Registered Successfully");
    }
    public void resetPass(User user){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        String username = user.getUsername();
        String password = user.getPassword();

        User user1 = new User(username,password);
        session.insert("User.resetPass",user1);
        session.commit();
        session.close();
        System.out.println("Password reset Successfully");
    }
    public static boolean isValidEmail(String email){// regex validation for email
        // Regex to check valid email.
        String regex = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
        // Compile the ReGex
        boolean b = Pattern.matches(regex, email);
        if (email == null) {
            return false;
        }
        return b;
    }
    public static boolean isValidPassword(String password){//regex Validation for password
        // Regex to check valid password.
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}";
        // Compile the ReGex
        boolean b1 = Pattern.matches(regex, password);
        if (password == null) {
            return false;
        }
        return b1;
    }
    public boolean loginMasuk(User user){//Login Method
       boolean hasil;
            boolean e = isValidEmail(user.getUsername());//get boolean regex for valid email
            boolean p = isValidPassword(user.getPassword());//get boolean regex for valid password
            System.out.println("Validation Process");
            if(e  &&  p){//Validate Email and Password Format Using Regex
                return hasil = true;
            }else{
                return hasil = false;
            }


    }
    public String claimToken(String SECRET, String PREFIX,String header){
        String jwtToken = header.replace(PREFIX, "");
        Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
        String currentLoggedUser = claims.get("sub").toString();
        return currentLoggedUser;
    }
    public String paymentHotel(String booking_id, String username){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        SqlSession session1 = MyBatisUtil.getSqlSessionFactory().openSession();
        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("Booking Id",booking_id);

        Booking payment = new Booking();
        payment = session.selectOne("User.payment", map);
        session.commit();
        session.close();
        if(payment == null){
            return "please make an order or check your booking id";
        }else if(payment.getPayment_status().equalsIgnoreCase("Done")){
            return "order has been paid, please make a new order";
        }else if(payment.getPayment_status().equalsIgnoreCase("Canceled")){
            return "order has been canceled, please make a new order";
        }else {
            Date date = new Date();
            Map<String,Object> mappay = new HashMap<String,Object>();
            mappay.put("username",username);
            mappay.put("status","Done");
            mappay.put("date",date);
            session1.update("User.updatePayment",mappay);
            session1.commit();
            session1.close();
            return "order has been paid, check your email for further information";
        }

    }
}
