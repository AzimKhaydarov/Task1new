package com.example.task1new.entity;

import lombok.Data;

@Data
public class RoomDto {
    private Integer number, floor, size;
    private Integer hotelId;
}
