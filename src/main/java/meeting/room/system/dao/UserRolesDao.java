package meeting.room.system.dao;

import meeting.room.system.model.User;

public class UserRolesDao extends BaseDao<User> {
    public UserRolesDao() {
        super(User.class);
    }
}
