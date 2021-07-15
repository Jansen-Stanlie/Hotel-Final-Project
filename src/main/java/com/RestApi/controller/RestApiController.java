package com.RestApi.controller;

import com.RestApi.rabbitmq.RestApiReceive;
import com.RestApi.rabbitmq.RestApiSend;
import com.RestApi.util.SuccessMessage;
import com.Stans.model.Booking;
import com.Stans.model.Hotel;
import com.Stans.model.User;
import com.Stans.repository.PaymentCheck;
import com.Stans.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.http.parser.Authorization;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import com.RestApi.util.CustomErrorType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/User")
public class RestApiController extends Thread {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";
    public User user = new User();
    private Timer timer = new Timer();
    UserRepo userRepo = new UserRepo();
    RestApiReceive receive = new RestApiReceive();
    RestApiSend send = new RestApiSend();

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody User user1) throws Exception {
        try {
           send.UserData(new Gson().toJson(user1),"LoginUser");
        }catch (Exception e){
            System.out.println("error = " + e);
        }
         user = receive.loginRecieve();
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new CustomErrorType("Login failed Username or Password Doesn't Match"), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> RegisterUser(@RequestBody User user1) throws Exception {
        UserRepo repo = new UserRepo();
        try {
            if(repo.loginMasuk(user1)==true){
                send.UserData(new Gson().toJson(user1),"RegisterUser");
            }else {
                return new ResponseEntity<>(new CustomErrorType("Wrong Password and Email Format"),HttpStatus.CREATED);
            }

        }catch (Exception e){
            System.out.println("error = " + e);
            return new ResponseEntity<>(new CustomErrorType("Password or Username Cannot Be empty"),HttpStatus.CREATED);
        }
       String hasil = receive.AllRecieve("MessageRegister");
        System.out.println(hasil);
        if(hasil.equalsIgnoreCase("Failed")){
            return new ResponseEntity<>(new CustomErrorType("Register Failed, Username is Existed"),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new SuccessMessage("Register Successfully"), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/forgetPass", method = RequestMethod.POST)
    public ResponseEntity<?> ForgetPassword(@RequestBody User user1) throws Exception {
        try {
            send.UserData(new Gson().toJson(user1),"ForgetPass");
        String token = getJWTTokenPass(user1.getUsername());
        user1.setToken(token);
        String hasil = receive.AllRecieve("MessagePass");
        if(hasil.equalsIgnoreCase("Success")){
            return new ResponseEntity<>(new SuccessMessage("Please Use this Token: " + user1.getToken()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new CustomErrorType("Please SignUp First"), HttpStatus.NOT_FOUND);
        }
        }catch (Exception e){
            System.out.println("error = " + e + " Database Not yet Starting");
            return new ResponseEntity<>(new CustomErrorType("Database Not yet Started"),HttpStatus.CREATED);
        }
    }
    @RequestMapping(value = "/Resetpass", method = RequestMethod.PUT)
    public ResponseEntity<?> ResetPass(@RequestBody User user1) throws Exception {
        UserRepo repo = new UserRepo();
        try {
            if(repo.loginMasuk(user1)==true){
                send.UserData(new Gson().toJson(user1),"ResetPass");
            }else {
                return new ResponseEntity<>(new CustomErrorType("Wrong Password Format"),HttpStatus.CREATED);
            }

        }catch (Exception e){
            System.out.println("error = " + e);
            return new ResponseEntity<>(new CustomErrorType("Password or Username Cannot Be empty"),HttpStatus.CREATED);
        }
            String hasil = receive.AllRecieve("Reseted");
            System.out.println(hasil);
        if(hasil.equalsIgnoreCase("Failed")){
            return new ResponseEntity<>(new CustomErrorType("Password Failed to reset"),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new CustomErrorType("Password has been reset"), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/AllhotelList", method = RequestMethod.GET)
    public List<Hotel> gethotelAll(){
        return Arrays.asList(restTemplate.getForObject("http://localhost:8080/hotel/All",Hotel[].class));
    }
    @RequestMapping(value = "/hotel/{location}", method = RequestMethod.GET)
    public ResponseEntity<?> gethotelbylocation(@PathVariable final String location)  {

        List<Hotel> hotels= Arrays.asList(restTemplate.getForObject("http://localhost:8080/hotel/"+ location,Hotel[].class));
        if(hotels.isEmpty()){
            return new ResponseEntity<>(new CustomErrorType("No hotel Listed in location "+ location)  ,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(hotels,HttpStatus.CREATED);
        }

    }
    @RequestMapping(value = "/hotel/Ratings/{ratings}", method = RequestMethod.GET)
    public ResponseEntity<?> gethotelbyratings(@PathVariable("ratings") final float rating)  {
        String ratings = Float.toString(rating) + " Star";
        List<Hotel> hotels= Arrays.asList(restTemplate.getForObject("http://localhost:8080/hotel/Ratings/"+ rating,Hotel[].class));
        if(hotels.isEmpty()){
            return new ResponseEntity<>(new CustomErrorType("No hotel with ratings " + ratings),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(hotels,HttpStatus.CREATED);
        }

    }

    @RequestMapping(value = "/hotel/Booking", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> Booking(@RequestBody JSONObject jobj, @RequestHeader(HEADER)  String header) throws RestClientException, JsonProcessingException {

        String username = userRepo.claimToken(SECRET,PREFIX,header);
        jobj.put("username",username);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        System.out.println(jobj.toString());
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(jobj, headers);
        JSONObject json = restTemplate.postForEntity("http://localhost:8080/hotel/Booking", formEntity, JSONObject.class).getBody();
        String message = String.valueOf(json.get("Booking Status"));

        if (!message.equalsIgnoreCase("Failed")) {
            System.out.println("json:" + json.toString());
            System.out.println(username);
            TimerTask tt = new PaymentCheck(username);
            timer.schedule(tt, 20000);
            return new ResponseEntity<>(json,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(json,HttpStatus.CREATED);
        }

    }
    @RequestMapping(value = "/hotel/Payment", method = RequestMethod.PUT)
    public ResponseEntity<?> payment(@RequestBody Booking book,@RequestHeader(HEADER)  String header) throws Exception {
        try {
             String username = userRepo.claimToken(SECRET,PREFIX,header);
             String booking_id = book.getBooking_id();
             String response = userRepo.paymentHotel(booking_id,username);
            return new ResponseEntity<>(new SuccessMessage(response),HttpStatus.OK);

        }catch (Exception e){
            System.out.println("error = " + e + " Database Not yet Starting");
            return new ResponseEntity<>(new CustomErrorType("Database Not yet Started"),HttpStatus.CREATED);
        }
    }

    private String getJWTTokenPass(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("Traveloka")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 50000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }
}
