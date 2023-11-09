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

@Service
@AllArgsConstructor
public class MeetingRoomService {

    private final MeetingRoomDao meetingRoomDao;
    private final BuildingDao buildingDao;

    @Transactional
    public List<MeetingRoom> findAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime) {
        return meetingRoomDao.findAvailableRooms(startTime, endTime);
    }
}
