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
            while (!elevator.getStackInside().isEmpty() || !elevator.getStackOutSide().isEmpty()) {
                log.info("-------------------------------------------");
                log.info("INSIDE" + elevator.getStackInside());
                log.info("OUTSIDE" + elevator.getStackOutSide());
                log.info("-------------------------------------------");
                elevator.setPendingMove(true);
                int currentFloor = elevator.getCurrentFloor();
                if (!elevator.getStackOutSide().isEmpty() && !elevator.getStackInside().isEmpty()) {
                    //Get the closest floor for InsideStack and OutsideStack
                    Call closestInside = closestFloor(elevator.getStackInside(), currentFloor);
                    Call closestOutside = closestFloor(elevator.getStackOutSide(), currentFloor);
                    //validate which is the closest
                    int closeOutSide = currentFloor - closestOutside.getFloor();
                    int closeInside = currentFloor - closestInside.getFloor();
                    if (closeInside > closeOutSide && closestInside.getDirection() == closestOutside.getDirection()) {
                        //Create a helper stack who will add the best option for the elevator this case adding the [outside floor]
                        List<Call> helperStack = elevator.getStackInside();
                        helperStack.add(0, closestOutside);
                        //Print the information to follow the algorithm steps
                        printData(helperStack,1);
                        moveElevator(helperStack, currentFloor, closestOutside.getFloor());
                        //After that, it has to remove the position added in the InsideStack and also remove in the OutsideStack
                        //because that information is already in the helperStack
                        removeInsideStack(closestOutside);
                        removeOutStack(closestOutside);
                    }else{
                        List<Call> helperStack = elevator.getStackInside();
                        printData(helperStack,2);
                        moveElevator(helperStack, currentFloor, closestInside.getFloor());
                        //This one just remove the inside stack value because does not have outsideStack to check
                        removeInsideStack(closestInside);
                    }
                } else if (!elevator.getStackInside().isEmpty() && elevator.getStackOutSide().isEmpty()) {
                    //Just In-side
                    List<Call> helperStack = elevator.getStackInside();
                    //Get the closest floor for InsideStack
                    Call closestInside = closestFloor(elevator.getStackInside(), currentFloor);
                    printData(helperStack,3);
                    moveElevator(helperStack, currentFloor, closestInside.getFloor());
                    //This one just remove the inside stack value because does not have outsideStack to check
                    removeInsideStack(closestInside);
                } else if (!elevator.getStackOutSide().isEmpty() && elevator.getStackInside().isEmpty()) {
                    //Just Out-side
                    List<Call> helperStack = elevator.getStackOutSide();
                    //Get the closest floor for OutStack
                    Call closestOutside = closestFloor(elevator.getStackOutSide(), currentFloor);
                    printData(helperStack,4);
                    moveElevator(helperStack, currentFloor, closestOutside.getFloor());
                    //This one just remove the out stack value because does not have inside to check
                    removeOutStack(closestOutside);
                }
                elevator.setPendingMove(false);
            }
        }
    }

    private void printData(List<Call> helperStack,int option) {
        log.info("----------------STACKS ["+option+"]----------------------");
        log.info(elevator.getStackInside().toString());
        log.info(elevator.getStackOutSide().toString());
        log.info("----------------HELPER [1]----------------------");
        log.info(helperStack.toString());
    }
    private void removeOutStack(Call closestOutside) {
        elevator.getStackOutSide().removeIf(call -> call.getFloor() == closestOutside.getFloor() && call.getDirection() == closestOutside.getDirection());
    }

    private void removeInsideStack(Call closestInside) {
        elevator.getStackInside().removeIf(call -> call.getFloor() == closestInside.getFloor() && call.getDirection() == closestInside.getDirection());
    }

    private Call closestFloor(List<Call> stack, int currentFloor) {
        //closet algorithm
        Comparator<Call> comparator = Comparator.comparingInt(a -> Math.abs(a.getFloor() - currentFloor));
        // Sort the list using the comparator
        stack.sort(comparator);
        return stack.get(0);
    }

    private void moveElevator(List<Call> stackInside, int currentFloor, int closest) throws InterruptedException {
        int direction = closest > currentFloor ? 1 : -1;
        Direction directionToCheck = closest > currentFloor ? Direction.UP : Direction.DOWN;
        // Iterate through the floors in the specified direction
        for (int destination = currentFloor; destination != closest; destination += direction) {
            //if the floor are in the list remove it
            floorToCheck(stackInside, destination, directionToCheck);
            log.info("Floor: " + (destination + direction));
            Thread.sleep(3000);
            elevator.setCurrentFloor(destination + direction);
        }
        log.info("moveElevator - Open the doors: " + elevator.getCurrentFloor());
    }

    private void floorToCheck(List<Call> stackInside, int floorToCheck, Direction directionToCheck) {
        boolean wasRemoved = stackInside.removeIf(call -> call.getFloor() == floorToCheck && call.getDirection() == directionToCheck);
        boolean wasRemoved2 = elevator.getStackOutSide().removeIf(call -> call.getFloor() == floorToCheck && call.getDirection() == directionToCheck);
        if (wasRemoved) {
            log.info("floorToCheck - Open the doors: " + floorToCheck);
        }
        if (wasRemoved2) {
            log.info("floorToCheck OutSide - Open the doors: " + floorToCheck);
        }
    }
}
