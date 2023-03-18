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
        while (!elevator.getStackCall().isEmpty()) {
            synchronized (elevator) {
                List<Call> stackCall = elevator.getStackCall();
                if (stackCall.isEmpty()) {
                    elevator.setPendingMove(false);
                    break;
                } else {
                    elevator.setPendingMove(true);
                    elevator.setDestinationFloor(stackCall.get(0).getFloor());

                    List<Call> callsSorted = sortStack(stackCall);
                    log.info(callsSorted.toString());
                    break;
                     /*
                    int positions= callsSorted.size();
                    for (int i = elevator.; i <positions; i++) {
                        try {
                            Thread.sleep(2000);

                        }catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                      */


                }
            }
        }
        log.info("was dead");
                /*
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                synchronized (elevator) {
                    log.info("VALUE:" + elevator.getCurrentFloor());
                    elevator.setCurrentFloor(i);
                    log.info("Current floor" + elevator.getCurrentFloor());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }*/

    }

    private List<Call> sortStack(List<Call> stackCall) {
        List<Call> callsList = new ArrayList<>(stackCall);
        if (stackCall.get(0).getDirection().equals(Direction.UP)) {
            callsList.sort(ElevatorThread::compareDesc);
        }else{
            callsList.sort(ElevatorThread::compareAsc);
        }
        return new LinkedList<>(callsList);
    }

    private static int compareDesc(Call c1, Call c2) {
        if (c1.getFloor() != c2.getFloor()) {
            return c2.getFloor() - c1.getFloor();
        } else {
            return c2.getDirection().compareTo(c1.getDirection());
        }
    }

    private static int compareAsc(Call c1, Call c2) {
        if (c1.getFloor() != c2.getFloor()) {
            return c1.getFloor() - c2.getFloor();
        } else {
            return c1.getDirection().compareTo(c2.getDirection());
        }
    }
}
