package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Elevator;

public interface IElevator {
    boolean callOutSide(Call call);
    boolean callInside(int moveElevator);
    Elevator showInfo();

}
