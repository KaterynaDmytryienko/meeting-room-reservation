package meeting.room.system.dao;

import meeting.room.system.model.User;

public class UserDao extends BaseDao<User> {
    public UserDao() {
            super(User.class);
    }

}
