package meeting.room.system.service;

import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.enums.PrioritizationStatus;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.enums.Roles;
import meeting.room.system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingRoomDao meetingRoomDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ReservationSystemService reservationSystemService;

    @Test
    public void createReservationTest() {
        Reservation reservation = new Reservation();
        MeetingRoom meetingRoom = meetingRoomDao.findById(1);

        User user = userDao.findByUsername("merlin1a");

        reservation.setUser(user);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(4));
        reservation.setMeetingRoom(meetingRoom);
        reservation.setMeetingRoomId(meetingRoom.getId());

        reservationSystemService.createReservation(reservation, user);

        assertThat(reservation.getStatus()).isEqualTo("Reserved");

    }

    @Test
    @Transactional
    public void persistUserTest() {
        User user = new User();
        user.setUserName("Kate123");
        user.setFirstName("Kate");
        user.setLastName("Kate");
        user.setEmail("kat@kat.com");
        user.setDateOfBirth(Date.valueOf("2009-05-04"));
        user.setPassword("1234");
        userService.persist(user);
        assertNotNull(user.getRoles());

    }
}
