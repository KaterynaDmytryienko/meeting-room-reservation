package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.enums.ReservationRules;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationSystemService {
    @Autowired
    private ReservationDao reservationDao;

@Transactional
    public boolean approveReservation(Reservation reservation) {
        Duration duration = Duration.between(LocalDateTime.now(), reservation.getStartTime());
        long days = duration.toDays();

        long reservationHours = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toHours();

        if (reservation.getUser() != null && reservation.getReservationTime() != null && reservation.getEndTime() != null &
                reservation.getMeetingRoom() != null && reservation.getStartTime() != null && days < ReservationRules.MAX_ADVANCE_BOOKING_DAYS
        && reservationHours >= ReservationRules.MIN_RESERVATION_LENGTH ||
                reservationHours <=  ReservationRules.MAX_RESERVATION_LENGTH) {

            return true;
        }
        return false;
    }
@Transactional
    public void cancelReservation(Reservation reservation) {
    Duration duration = Duration.between(LocalDateTime.now(), reservation.getStartTime());
    long hours = duration.toHours();
        if (reservation != null && hours >= ReservationRules.MAX_CANCELLATION_TIME) {
            reservationDao.remove(reservation);
        } else if (hours < ReservationRules.MAX_CANCELLATION_TIME) {
            reservation.getUser().setBanned(true);
        }
}
@Transactional
    public void updateReservation(LocalDateTime startTime, LocalDateTime endTime, Reservation reservation, MeetingRoomDao meetingRoomDao) {
        if (!meetingRoomDao.findAvailableRooms(startTime, endTime).isEmpty() && startTime != null & endTime != null) {
            reservation.setStartTime(startTime);
        }

    }
}
