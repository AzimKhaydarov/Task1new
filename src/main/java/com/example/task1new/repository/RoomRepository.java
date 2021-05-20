package com.example.task1new.repository;

import com.example.task1new.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByHotel_IdAndFloorAndNumberAndSize(Integer hotel_id, Integer floor, Integer roomNumber, Integer roomSize);
    Page<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);


    List<Room> findAllByHotel_Id(Integer hotel_id);
}
