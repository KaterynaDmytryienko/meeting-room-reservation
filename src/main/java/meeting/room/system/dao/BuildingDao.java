package meeting.room.system.dao;

import meeting.room.system.model.Building;
import org.springframework.stereotype.Repository;

@Repository
public class BuildingDao extends BaseDao<Building> {

    protected BuildingDao() {
        super(Building.class);
    }
}
