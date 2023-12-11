package meeting.room.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends AbstractEntity{
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime reservationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

}
