package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import meeting.room.system.service.ReservationSystemService;
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
    @PersistenceContext
    EntityManager em;

    @Test
    public void approveReservationTest() {
        ReservationSystemService reservationSystemService = new ReservationSystemService();
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

        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(3));
        reservation.setMeetingRoom(meetingRoom);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setUser(user);
        assertThat(reservationSystemService.approveReservation(reservation)).isTrue();
    }

    @Test
    public void cancelReservationTest() {
        ;
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

        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(3));
        reservation.setMeetingRoom(meetingRoom);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setUser(user);

        em.persist(reservation);

        em.remove(reservation);
        assertThat(reservationDao.find(reservation.getId())).isNull();
    }
}
