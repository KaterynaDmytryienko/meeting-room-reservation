package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.enums.PrioritizationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MeetingRoomServiceTest {

    @Autowired
    private MeetingRoomDao meetingRoomDao;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testFindAvailableMeetingRooms() {

        LocalDateTime startTime = LocalDateTime.of(2023, 10, 3, 6, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 4, 15, 0);

        MeetingRoom room1 = createMeetingRoom("Room 1", 10, 1, PrioritizationStatus.LOW);
        MeetingRoom room2 = createMeetingRoom("Room 2", 50, 1, PrioritizationStatus.HIGH);
        MeetingRoom room3 = createMeetingRoom("Room 3", 2, 0, PrioritizationStatus.HIGH);
        meetingRoomDao.persist(room1);
        meetingRoomDao.persist(room2);
        meetingRoomDao.persist(room3);

        List<MeetingRoom> availableRooms = meetingRoomDao.findAvailableRooms(startTime, endTime);

        assertThat(availableRooms).hasSize(3).extracting(MeetingRoom::getRoomName)
                .containsExactlyInAnyOrder("Conference Room D", "Conference Room HD", "Room 2");
    }

    private MeetingRoom createMeetingRoom(String name, int capacity, Integer isAvailable, PrioritizationStatus status) {
        MeetingRoom room = new MeetingRoom(name, capacity, isAvailable, status);
        return room;
    }
}

