package com.jestebandev.AscensorAPI.service;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Direction;
import com.jestebandev.AscensorAPI.model.Elevator;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ElevatorThread extends Thread {
    private final Elevator elevator;

    public ElevatorThread(Elevator elevator) {
        this.elevator = elevator;
    }

    public void run() {
        while (true) {
            try {
                log.info("WAITING...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
                //Remove the closet value will do that operation

                log.info(stackInside.toString());
                log.info("closest" + closest);
                while (closest != currentFloor) {
                    if (closest > currentFloor) {
                        for (int i = currentFloor; i <= closest; i++) {
                            try {
                                log.info("INCREMENT="+i);
                                Thread.sleep(2000);
                                elevator.setCurrentFloor(i);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        stackInside.remove(0);
                        elevator.setStackInside(stackInside);
                        break;
                    } else {
                        for (int i = currentFloor; i >= closest; i--) {
                            try {
                                log.info("DECREMENT="+i);
                                Thread.sleep(2000);
                                elevator.setCurrentFloor(i);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        stackInside.remove(0);
                        elevator.setStackInside(stackInside);
                        break;
                    }
                }
                elevator.setPendingMove(false);
                log.info("POSITION"+elevator.getCurrentFloor());
                break;
            }
        }

    }
}
