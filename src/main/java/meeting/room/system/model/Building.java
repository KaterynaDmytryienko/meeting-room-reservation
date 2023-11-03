package meeting.room.system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table
public class Building extends AbstractEntity {
    @ManyToOne
    private Adress adress;
    private String buildingName;
    private int buildingCapacity;

    @OneToMany
    private List<MeetingRoom> meetingRooms;

    public Adress getAdress() {
        return adress;
    }

    public void setAddress(Adress adress) {
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
