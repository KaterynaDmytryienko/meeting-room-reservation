package meeting.room.system.dao;

import meeting.room.system.model.MeetingRoom;

public class MeetingRoomDao extends BaseDao<MeetingRoom>{
    protected MeetingRoomDao() {
        super(MeetingRoom.class);
    }

//    public List<MeetingRoom>findAllAvailable(){
//        return em.createQuery("SELECT m FROM MeetingRoom m WHERE m.isAvailable = FALSE", MeetingRoom.class).getResultList();
//    }
}
