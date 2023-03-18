package com.jestebandev.AscensorAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Call {
    int floor;
    Direction direction;

    @Override
    public String toString() {
        return "Call{" +
                "floor=" + floor +
                ", direction=" + direction + '}';
    }
}
