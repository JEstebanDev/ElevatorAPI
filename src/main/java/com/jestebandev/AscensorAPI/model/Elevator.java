package com.jestebandev.AscensorAPI.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Elevator {
    int currentFloor;
    int destinationFloor;
    boolean pendingMove;
    List<Call> stackInside;
    List<Call> stackOutSide;

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

    public List<Call> getStackInside() {
        return stackInside;
    }

    public void setStackInside(List<Call> stackInside) {
        this.stackInside = stackInside;
    }

    public void addStackInside(Call stackInside) {
        this.stackInside.add(stackInside);
    }

    public List<Call> getStackOutSide() {
        return stackOutSide;
    }

    public void addStackOutSide(Call stackOutSide) {
        this.stackOutSide.add(stackOutSide);
    }

    public void setStackOutSide(Call stackOutSide) {
        this.stackOutSide.add(stackOutSide);
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                ", destinationFloor=" + destinationFloor +
                ", pendingMove=" + pendingMove +
                ", stackInside=" + stackInside +
                ", stackOutSide=" + stackOutSide +
                '}';
    }
}
