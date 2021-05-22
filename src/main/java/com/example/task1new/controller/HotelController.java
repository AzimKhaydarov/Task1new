package com.example.task1new.controller;

import com.example.task1new.entity.Hotel;
import com.example.task1new.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Object hotelOnePageable(@PathVariable Integer id) {
        if (!hotelRepository.findById(id).isPresent()) {
            return "The hotel with current Id does not exist!";
        }
        Optional<Hotel> byId = hotelRepository.findById(id);
        Hotel hotel = byId.get();
        return hotel;
    }


    @PostMapping("/add")
    public String addHotel(@RequestBody Hotel newHotel) {
        for (Hotel hotel : hotelRepository.findAll()) {
            if (hotel.getName().equals(newHotel.getName())) {
                return "this hotel already exist";
            }
        }
        hotelRepository.save(newHotel);
        return "Hotel successfully added!";
    }

    @PutMapping("/edit/{hotelId}")
    public String setHotel(@PathVariable Integer hotelId, @RequestBody Hotel newHotel) {
        Optional<Hotel> byId = hotelRepository.findById(hotelId);
        if (byId.isPresent()) {
            if (hotelRepository.existsByName(newHotel.getName())){
                return "The Hotel already exist in hotel list!";
            }
            Hotel hotel = byId.get();
            hotel.setName(newHotel.getName());
            hotelRepository.save(hotel);
            return "Hotel edited successfully";
        }
        return "not found this id";

    }

    @DeleteMapping("/del/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        if (hotelRepository.findById(id).isPresent()){
            hotelRepository.deleteById(id);
            return "successfully deleted";
        }
        return "hotel not found";
    }
}
