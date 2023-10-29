package model;

import jakarta.persistence.*;

@Entity
public class MeetingRoom extends AbstractEntity{

    private String roomName;
    private int capacity;
    private boolean isAvailable;
    private PrioritizationStatus prioritizationStatus;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public PrioritizationStatus getPrioritizationStatus() {
        return prioritizationStatus;
    }

    public void setPrioritizationStatus(PrioritizationStatus prioritizationStatus) {
        this.prioritizationStatus = prioritizationStatus;
    }


}
