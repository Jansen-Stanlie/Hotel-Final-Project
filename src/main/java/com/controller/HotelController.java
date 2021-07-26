package com.controller;

import com.model.Booking;
import com.model.Hotel;
import com.repository.hotelrepo;
import com.service.CustomErrorType;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    hotelrepo hotelrepo;
    public static final Logger logger = LoggerFactory.getLogger(HotelController.class);


    @RequestMapping(value = "/All", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Hotel>> listAllHotel() {
        List<Hotel> hotels = hotelrepo.findAll();
        if (hotels.isEmpty()) {
            logger.error("No data in database");
            return new ResponseEntity<>(hotels , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @RequestMapping(value = "/{location}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<?> GetHotelbyLocation(@PathVariable("location") String location) {
        logger.info("Fetching Hotel list by location in {}",location);
        List<Hotel> hotels = hotelrepo.findByLocation(location);

        return new ResponseEntity<>(hotels,HttpStatus.OK);
    }
    @RequestMapping(value = "Ratings/{ratings}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<?> GetHotelbyratings(@PathVariable("ratings") float rating) {
        String ratings = Float.toString(rating) + " Star";
        logger.info("Fetching Hotel list by Ratings {}",ratings);
        List<Hotel> hotels = hotelrepo.findByRatings(ratings);

        return new ResponseEntity<>(hotels,HttpStatus.OK);
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Hotel>> GetHotelbyroom(@RequestParam("room") String room, @RequestParam("location") String location){
        logger.info("Fetching Hotel list by room {}",room);
        List<Hotel> hotels = hotelrepo.findByRoom(room,location);
        return new ResponseEntity<>(hotels,HttpStatus.OK);
    }

    @RequestMapping(value = "/Booking", method = RequestMethod.POST)
    public ResponseEntity<?> insertStudent(@RequestBody JSONObject jobj) throws IOException, ParseException, java.text.ParseException {
        JSONObject book = hotelrepo.bookDetail(jobj);
        System.out.println(jobj.toString());
            return (new ResponseEntity<>(book, HttpStatus.CREATED));
    }


}
