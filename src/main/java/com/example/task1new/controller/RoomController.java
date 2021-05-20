package com.example.task1new.controller;

import com.example.task1new.entity.Hotel;
import com.example.task1new.entity.Room;
import com.example.task1new.entity.RoomDto;
import com.example.task1new.repository.HotelRepository;
import com.example.task1new.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;


    @GetMapping("/{hotelId}")
    public Object roomOnePageable(@PathVariable Integer hotelId, @RequestParam int page) {
        if (!hotelRepository.findById(hotelId).isPresent()) {
            return "The room with current id doesn't exist!";
        }
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> allByHotel_id = roomRepository.findAllByHotel_Id(hotelId, pageable);

        return allByHotel_id;
    }

    @GetMapping("/all")
    public Object roomAllPageable(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAll(pageable);
    }

    @DeleteMapping("/del/{roomId}")
    public String deleteRoom(@PathVariable Integer roomId) {
        if (!roomRepository.findById(roomId).isPresent()) {
            return "room not found";
        }
        roomRepository.deleteById(roomId);
        return "successfully deleted";
    }

    @PostMapping("/add")
    public String addRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "hotel not found";
        if (roomRepository.existsByHotel_IdAndFloorAndNumberAndSize(roomDto.getHotelId(), roomDto.getFloor(), roomDto.getNumber(), roomDto.getSize()))
            return "Current room already exists in this hotel!";
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotelRepository.getOne(roomDto.getHotelId()));
        roomRepository.save(room);
        return "room successfully added";
    }

    @PutMapping("/add/{roomId}")
    public String setRoom(@PathVariable Integer roomId, @RequestBody RoomDto roomDto) { //Faqatgina Hotel Id jo'natiladi
        Optional<Room> byId = roomRepository.findById(roomId);
        if (!byId.isPresent()) return "The room with current id not found";
        Room room1 = byId.get();
        if (!hotelRepository.findById(roomDto.getHotelId()).isPresent()) return "The hotel with current id not found";
        room1.setNumber(roomDto.getNumber());
        room1.setFloor(roomDto.getFloor());
        room1.setSize(roomDto.getSize());
        room1.setHotel(hotelRepository.getOne(roomDto.getHotelId()));
        if (roomRepository.existsByHotel_IdAndFloorAndNumberAndSize(roomDto.getHotelId(), roomDto.getFloor(), roomDto.getNumber(), roomDto.getSize()))
            return "Current room already exists in this hotel!";
        roomRepository.save(room1);
        return "The room edited successfully";
    }
}
