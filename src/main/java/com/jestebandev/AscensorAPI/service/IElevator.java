package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Elevator;
import com.jestebandev.AscensorAPI.model.MoveElevator;

public interface IElevator {
    boolean callOutSide(Call call);
    boolean callInside(MoveElevator moveElevator);
    Elevator showInfo();

}
