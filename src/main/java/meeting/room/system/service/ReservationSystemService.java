package meeting.room.system.service;

import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationSystemService {

    public boolean approveReservation(Reservation reservation) {
        if (reservation.getUser() != null && reservation.getReservationTime() != null && reservation.getEndTime() != null &
                reservation.getMeetingRoom() != null && reservation.getStartTime() != null) {
            return true;
        }
        return false;
    }

    public void cancelReservation(Reservation reservation, ReservationDao reservationDao) {
        if (reservation != null) {
            reservationDao.remove(reservation);
            reservation.getUser().getReservations().remove(reservation);
        }
    }

    public void updateReservation(LocalDateTime startTime, LocalDateTime endTime, Reservation reservation, MeetingRoomDao meetingRoomDao) {
        if (!meetingRoomDao.findAvailableRooms(startTime, endTime).isEmpty() && startTime != null & endTime != null) {
            reservation.setStartTime(startTime);
        }

    }
}
