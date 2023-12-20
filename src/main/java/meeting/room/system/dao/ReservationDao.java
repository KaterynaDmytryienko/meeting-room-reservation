package meeting.room.system.dao;

import jakarta.persistence.TypedQuery;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MeetingRoomDao meetingRoomDao;
    public List<Reservation> getAllReservationsForMeetingRoom(int meetingRoomId){
        MeetingRoom meetingRoom = meetingRoomDao.findById(meetingRoomId);
        TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.meetingRoom = :meetingRoom", Reservation.class);
        query.setParameter("meetingRoom", meetingRoom);
        return query.getResultList();

    }
    public List<Reservation> findOverlappingReservations(LocalDateTime start, LocalDateTime end, Integer roomId) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.meetingRoom.id = :roomId AND (r.startTime < :end AND r.endTime > :start)", Reservation.class)
                .setParameter("roomId", roomId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

//    public List<Reservation> getAllByUserId(int id) {
//        return em.createQuery("SELECT r FROM Reservation r WHERE r.user.id = :id", Reservation.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
//public List<Reservation> getAllByUserId(int userId) {
//    return em.createQuery("SELECT r FROM Reservation r INNER JOIN MeetingRoom m ON r.meetingRoom.id = m.id WHERE r.user.id = :userId", Reservation.class)
//            .setParameter("userId", userId)
//            .getResultList();
//}

    public List<Reservation> getAllByUserId(int userId) {
        List<Reservation> reservations = em.createQuery("SELECT r FROM Reservation r INNER JOIN r.meetingRoom m WHERE r.user.id = :userId", Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
        for (Reservation reservation : reservations) {
            if (reservation.getMeetingRoom() != null) {
                reservation.setMeetingRoomId(reservation.getMeetingRoom().getId());
            }
        }
        return reservations;
    }

    public Reservation getByReservationId(int reservationId) {
        Reservation reservation = em.createQuery("SELECT r FROM Reservation r INNER JOIN r.meetingRoom m WHERE r.id = :reservationId", Reservation.class)
                .setParameter("reservationId", reservationId).getSingleResult();
                reservation.setMeetingRoomId(reservation.getMeetingRoom().getId());
        return reservation;
    }


    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = em.createQuery("SELECT r FROM Reservation r INNER JOIN r.meetingRoom m", Reservation.class)
                .getResultList();
        for (Reservation reservation : reservations) {
            if (reservation.getMeetingRoom() != null) {
                reservation.setMeetingRoomId(reservation.getMeetingRoom().getId());
            }
        }
        return reservations;
    }




}
