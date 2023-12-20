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
@AllArgsConstructor
@NoArgsConstructor
public class Building extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false, name = "building_name")
    private String buildingName;

    @Column(nullable = false, name = "building_capacity")
    private int buildingCapacity;

    @OneToMany
    private List<MeetingRoom> meetingRooms;
}
