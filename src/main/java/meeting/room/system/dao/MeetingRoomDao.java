package meeting.room.system.dao;

import jakarta.persistence.TypedQuery;
import meeting.room.system.model.MeetingRoom;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MeetingRoomDao extends BaseDao<MeetingRoom> {
    public MeetingRoomDao() {
        super(MeetingRoom.class);
    }

    public List<MeetingRoom> findAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        TypedQuery<MeetingRoom> query = em.createQuery(
                "SELECT m FROM MeetingRoom m WHERE NOT EXISTS (" +
                        "SELECT r FROM Reservation r WHERE " +
                        "r.meetingRoom = m AND " +
                        "(r.startTime < :endTime AND r.endTime > :startTime)" +
                        ")", MeetingRoom.class);

        // Convert LocalDateTime to Timestamp
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);

        return query.getResultList();
    }
}
