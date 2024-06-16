package meeting.room.system.dao;

import jakarta.persistence.TypedQuery;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.enums.PrioritizationStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingRoomDao extends BaseDao<MeetingRoom> {
    public MeetingRoomDao() {
        super(MeetingRoom.class);
    }

    public List<MeetingRoom> findAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<MeetingRoom> availableRooms = new ArrayList<>();

        //from highest to lowest
        for (PrioritizationStatus status : PrioritizationStatus.values()) {
            TypedQuery<MeetingRoom> query = em.createQuery(
                    "SELECT m FROM MeetingRoom m WHERE m.prioritizationStatus = :status AND m.isAvailable = 1 AND NOT EXISTS (" +
                            "SELECT r FROM Reservation r WHERE " +
                            "r.meetingRoom = m AND " +
                            "(r.startTime < :endTime AND r.endTime > :startTime)" +
                            ")", MeetingRoom.class);
            query.setParameter("status", status);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            List<MeetingRoom> rooms = query.getResultList();
            if (!rooms.isEmpty()) {
                return rooms;
            }
        }

        return availableRooms;
    }

    public List<MeetingRoom> getAllMeetingRooms(){
        return em.createQuery(
                "SELECT m FROM MeetingRoom m ").getResultList();
    }
    public MeetingRoom findById(int id){
        return find(id);
    }

    public List<MeetingRoom>getAllAvailableRooms(){
        return em.createQuery("SELECT m FROM MeetingRoom m WHERE m.isAvailable = 1", MeetingRoom.class)
                .getResultList();
    }
}
