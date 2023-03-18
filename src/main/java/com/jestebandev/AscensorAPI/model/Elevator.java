package com.jestebandev.AscensorAPI.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Elevator {
    int currentFloor;
    int destinationFloor;
    boolean pendingMove;
    List<Call> stackCall;

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public synchronized int getDestinationFloor() {
        return destinationFloor;
    }

    public synchronized void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public synchronized boolean isPendingMove() {
        return pendingMove;
    }

    public synchronized void setPendingMove(boolean pendingMove) {
        this.pendingMove = pendingMove;
    }

    public List<Call> getStackCall() {
        return stackCall;
    }

    public void setStackCall(Call stackCall) {
        this.stackCall.add(stackCall);
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                ", destinationFloor=" + destinationFloor +
                ", pendingMove=" + pendingMove +
                ", stackCall=" + stackCall +
                '}';
    }
}
