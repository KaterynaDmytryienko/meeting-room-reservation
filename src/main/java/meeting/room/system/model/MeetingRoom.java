package meeting.room.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import meeting.room.system.enums.PrioritizationStatus;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class MeetingRoom extends AbstractEntity{

    @JsonBackReference
    @OrderBy("startTime desc")
    @OneToMany(mappedBy = "meetingRoom")
    private List<Reservation> reservations;

    @Column(nullable = false, name = "room_name")
    private String roomName;

    @Column(nullable = false, name = "capacity")
    private int capacity;

    @Column(nullable = false, name = "is_available")
    private Integer isAvailable;

    @Column(nullable = false, name = "prioritization_status")
    private PrioritizationStatus prioritizationStatus;

    public MeetingRoom(String roomName, int capacity, Integer isAvailable, PrioritizationStatus prioritizationStatus) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
        this.prioritizationStatus = prioritizationStatus;
    }

    public MeetingRoom() {
    }
}
