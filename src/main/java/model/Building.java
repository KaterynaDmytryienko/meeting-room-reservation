package model;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Building extends AbstractEntity {
    private Adress adress;
    private String buildingName;
    private int buildingCapacity;
    private List<MeetingRoom> meetingRooms;

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingCapacity() {
        return buildingCapacity;
    }

    public void setBuildingCapacity(int buildingCapacity) {
        this.buildingCapacity = buildingCapacity;
    }

    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
    }
}
