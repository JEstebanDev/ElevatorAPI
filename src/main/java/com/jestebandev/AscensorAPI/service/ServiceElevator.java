package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Slf4j
@Service
public class ServiceElevator implements IElevator {
    Elevator elevator = new Elevator(1, 0, false, new LinkedList<>(), new LinkedList<>());
    ElevatorThread elevatorThread = new ElevatorThread(elevator);

    ServiceElevator(){
        elevatorThread.start();
    }
    @Override
    public void callOutSide(Call call) {
        elevator.addStackOutSide(call);
    }
    @Override
    public void callInside(int moveElevator) {
        if (elevator.getCurrentFloor() < moveElevator) {
            elevator.addStackInside(new Call(moveElevator, Direction.UP));
        } else {
            elevator.addStackInside(new Call(moveElevator, Direction.DOWN));
        }
    }
    @Override
    public Elevator showInfo() {
        return elevator;
    }
}
