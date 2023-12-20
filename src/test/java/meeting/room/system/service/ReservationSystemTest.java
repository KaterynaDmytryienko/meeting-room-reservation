package meeting.room.system.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class ReservationSystemTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationSystemService reservationSystemService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MeetingRoomDao meetingRoomDao;

    @Test
    public void approveReservationTest() {
        Reservation reservation = new Reservation();

        MeetingRoom meetingRoom = meetingRoomDao.findById(1);
        User user = userDao.findByUsername("merlin1a");
        reservation.setStartTime(LocalDateTime.now().plusHours(5));
        reservation.setEndTime(LocalDateTime.now().plusHours(10));
        reservation.setMeetingRoom(meetingRoom);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setUser(user);
        assertThat(reservationSystemService.approveReservation(reservation)).isTrue();
    }

    @Test
    public void cancelReservationTest() {
        Reservation reservation = new Reservation();
        User user = new User();

       MeetingRoom meetingRoom = meetingRoomDao.findById(1);

       User user1 = userDao.findByUsername("merlin1a");

        reservation.setStartTime(LocalDateTime.now().plusHours(3));
        reservation.setEndTime(LocalDateTime.now());
        reservation.setMeetingRoom(meetingRoom);
        reservation.setReservationTime(LocalDateTime.now().plusHours(5));
        reservation.setUser(user1);
        reservation.setStatus("Reserved");

        reservationDao.persist(reservation);
        int reservationID = reservation.getId();
        reservationSystemService.cancelReservation(reservation);
        assertThat(reservationDao.find(reservationID)).isNull();
    }
}
