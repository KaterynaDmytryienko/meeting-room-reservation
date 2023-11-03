package meeting.room.system.service;

import meeting.room.system.dao.BuildingDao;
import meeting.room.system.dao.MeetingRoomDao;

public class MeetingRoomService {
private final MeetingRoomDao meetingRoomDao;
private final BuildingDao buildingDao;

    public MeetingRoomService(MeetingRoomDao meetingRoomDao, BuildingDao buildingDao) {
        this.meetingRoomDao = meetingRoomDao;
        this.buildingDao = buildingDao;
    }
}
