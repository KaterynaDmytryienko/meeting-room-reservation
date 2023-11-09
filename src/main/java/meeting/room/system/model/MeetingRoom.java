package meeting.room.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class MeetingRoom extends AbstractEntity{

    @OneToMany(mappedBy = "meetingRoom")
    private List<Reservation> reservations;
    private String roomName;
    private int capacity;
    private boolean isAvailable;
    private PrioritizationStatus prioritizationStatus;

    public MeetingRoom(String roomName, int capacity, boolean isAvailable, PrioritizationStatus prioritizationStatus) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
        this.prioritizationStatus = prioritizationStatus;
    }

    public MeetingRoom() {
    }
}
