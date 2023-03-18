package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Slf4j
@Service
public class ServiceElevator implements IElevator {
    Elevator elevator = new Elevator(3, 0, false, new LinkedList<>());

    @Override
    public boolean callOutSide(Call call) {
        return false;
    }

    @Override
    public boolean callInside(MoveElevator moveElevator) {
        if (elevator.getCurrentFloor() < moveElevator.getDestinationFloor()) {
            elevator.setStackCall(new Call(moveElevator.getDestinationFloor(), Direction.UP, Priority.INSIDE));
        } else {
            elevator.setStackCall(new Call(moveElevator.getDestinationFloor(), Direction.DOWN, Priority.INSIDE));
        }
        ElevatorThread elevatorThread = new ElevatorThread(elevator);
        elevatorThread.start();
        return false;
    }

    @Override
    public Elevator showInfo() {
        return elevator;
    }
}
