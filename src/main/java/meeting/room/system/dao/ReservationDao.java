package meeting.room.system.dao;

import jakarta.persistence.TypedQuery;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class ReservationDao extends BaseDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }
    public List<Reservation> getAllReservationsForMeetingRoom(MeetingRoom meetingRoom, Date date){
        LocalDateTime startOfDay = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atStartOfDay();

        LocalDateTime endOfDay = startOfDay.plusDays(1);

        TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.meetingRoom = :meetingRoom AND r.startTime >= :startOfDay AND r.endTime < :endOfDay", Reservation.class);
        query.setParameter("meetingRoom", meetingRoom);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);

        return query.getResultList();

    }
}
