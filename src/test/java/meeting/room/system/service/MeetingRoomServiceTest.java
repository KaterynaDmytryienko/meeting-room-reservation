package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.PrioritizationStatus;
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

        LocalDateTime startTime = LocalDateTime.of(2023, 12, 3, 6, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 4, 15, 0);

        MeetingRoom room1 = createMeetingRoom("Room 1", 10, true, PrioritizationStatus.LOW);
        MeetingRoom room2 = createMeetingRoom("Room 2", 50, true, PrioritizationStatus.HIGH);
        MeetingRoom room3 = createMeetingRoom("Room 3", 2, true, PrioritizationStatus.HIGH);
        em.persist(room1);
        em.persist(room2);
        em.persist(room3);

        List<MeetingRoom> availableRooms = meetingRoomDao.findAvailableRooms(startTime, endTime);

        assertThat(availableRooms).hasSize(3).extracting(MeetingRoom::getRoomName)
                .containsExactlyInAnyOrder(room1.getRoomName(), room2.getRoomName(), room3.getRoomName());
    }

    private MeetingRoom createMeetingRoom(String name, int capacity, boolean isAvailable, PrioritizationStatus status) {
        MeetingRoom room = new MeetingRoom(name, capacity, isAvailable, status);
        return room;
    }
}

