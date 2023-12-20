package meeting.room.system.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meeting.room.system.dao.BuildingDao;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.model.MeetingRoom;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MeetingRoomService {

    private final MeetingRoomDao meetingRoomDao;
    private final BuildingDao buildingDao;

    @Transactional
    public List<MeetingRoom> findAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime) {
        return meetingRoomDao.findAvailableRooms(startTime, endTime);
    }


    @Transactional
    public MeetingRoom createMeetingRoom(MeetingRoom meetingRoom) {
        Objects.requireNonNull(meetingRoom);
        final MeetingRoom meetingRoom1 = meetingRoom;

        meetingRoomDao.persist(meetingRoom1);
        return meetingRoom1;
    }

}
