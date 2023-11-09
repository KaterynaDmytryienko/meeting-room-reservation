package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.Roles;
import meeting.room.system.model.User;
import meeting.room.system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testReservation() {
        Reservation reservation = new Reservation();
        User user = new User();

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setRoomName("Stark");
        meetingRoom.setCapacity(2);
        meetingRoom.setAvailable(true);

        user.setFirstName("Martina");
        user.setEmail("kat@dmitr.com");
        user.setLastName("Novotna");
        user.setDateOfBirth(Date.valueOf("2009-05-04"));
        user.setPassword("12345");

        reservation.setUser(user);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(4));
        reservation.setMeetingRoom(meetingRoom);

        userService.createReservation(reservation);

        assertThat(reservation.getStatus()).isEqualTo("Reserved");
        assertThat(reservation.getMeetingRoom().isAvailable()).isFalse();

    }

    @Test
    @Transactional
    public void persistUserTest() {
        User user = new User();
        user.setUserName("Kate123");
        user.setFirstName("Kate");
        user.setEmail("kat@kat.com");
        user.setDateOfBirth(Date.valueOf("2009-05-04"));
        user.setPassword("1234");
        userService.persist(user);
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().contains(Roles.USER));

    }
}
