package com.example.task1new.repository;

import com.example.task1new.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);
}
