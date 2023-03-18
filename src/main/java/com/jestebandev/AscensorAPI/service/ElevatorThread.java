package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Direction;
import com.jestebandev.AscensorAPI.model.Elevator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ElevatorThread extends Thread {
    private final Elevator elevator;

    public ElevatorThread(Elevator elevator) {
        this.elevator = elevator;
    }

    @SneakyThrows
    public void run() {
        while (true) {
            log.info("WAITING...");
            Thread.sleep(2000);
            while (!elevator.getStackInside().isEmpty()) {
                elevator.setPendingMove(true);
                List<Call> stackInside = elevator.getStackInside();
                int currentFloor = elevator.getCurrentFloor();
                //closet algorithm
                Comparator<Call> comparator = Comparator.comparingInt(a -> Math.abs(a.getFloor() - currentFloor));
                // Sort the list using the comparator
                stackInside.sort(comparator);
                // Retrieve the closest value
                int closest = stackInside.get(0).getFloor();


                log.info(stackInside.toString());
                log.info("closest" + closest);
                //It will go to each floor
                while (closest != currentFloor) {
                    int direction = closest > currentFloor ? 1 : -1;
                    // Iterate through the floors in the specified direction
                    for (int destination = currentFloor; destination != closest; destination += direction) {
                            log.info("Floor: " + destination);
                            Thread.sleep(2000);
                            elevator.setCurrentFloor(destination+direction);
                    }
                    //Remove the closet value because is already in that floor
                    stackInside.remove(0);
                    //Set the new stack sorted in the principle stack
                    elevator.setStackInside(stackInside);
                    break;
                }
                elevator.setPendingMove(false);
                log.info("POSITION" + elevator.getCurrentFloor());
                break;
            }
        }

    }
}
