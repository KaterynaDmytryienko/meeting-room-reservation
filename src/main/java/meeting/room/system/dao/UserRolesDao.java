package meeting.room.system.dao;

import meeting.room.system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRolesDao extends BaseDao<User> {
    public UserRolesDao() {
        super(User.class);
    }
}
