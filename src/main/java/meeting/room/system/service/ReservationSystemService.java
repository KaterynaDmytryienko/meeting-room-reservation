package meeting.room.system.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.enums.ReservationRules;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationSystemService {
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private MeetingRoomDao meetingRoomDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public boolean isBusyAtSetTime(Reservation reservation){
        LocalDateTime proposedStartTime = reservation.getStartTime();
        LocalDateTime proposedEndTime = reservation.getEndTime();
        Integer roomId = reservation.getMeetingRoom().getId();

        List<Reservation> overlappingReservations = reservationDao.findOverlappingReservations(proposedStartTime, proposedEndTime, roomId);

        return !overlappingReservations.isEmpty();
    }

@Transactional
    public boolean approveReservation(Reservation reservation) {
        Duration duration = Duration.between(LocalDateTime.now(), reservation.getStartTime());
        long days_prior_to_reserved_date = duration.toDays();

        long reservationHours = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toHours();

        if(isBusyAtSetTime(reservation)){return false;}


        if (reservation.getUser() != null && reservation.getReservationTime() != null && reservation.getEndTime() != null &&
                reservation.getMeetingRoom() != null && reservation.getStartTime() != null && days_prior_to_reserved_date < ReservationRules.MAX_ADVANCE_BOOKING_DAYS
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
            return;
        }
        if (hours < ReservationRules.MAX_CANCELLATION_TIME) {
            User user = reservation.getUser();
            userDao.banUser(user.getId());
            reservationDao.remove(reservation);
        }
}
@Transactional
    public void updateReservation(LocalDateTime startTime, LocalDateTime endTime, Reservation reservation, MeetingRoomDao meetingRoomDao) {
        if (!meetingRoomDao.findAvailableRooms(startTime, endTime).isEmpty() && startTime != null & endTime != null) {
            reservation.setStartTime(startTime);
        }

    }

    @Transactional
    public boolean createReservation(Reservation reservation, User user) {
        Objects.requireNonNull(reservation);

        MeetingRoom meetingRoom = meetingRoomDao.findById(reservation.getMeetingRoomId());
        reservation.setMeetingRoom(meetingRoom);

        if (approveReservation(reservation) && user.getIsBanned()!=1) {
            reservation.setUser(user);
            reservation.setStatus("Reserved");

            reservationDao.persist(reservation);
            return true;
        }
        return false;
    }

}
