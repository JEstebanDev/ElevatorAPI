package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Elevator;

public interface IElevator {
    void callOutSide(Call call);
    void callInside(int moveElevator);
    Elevator showInfo();

}
