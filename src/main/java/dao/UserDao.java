package dao;

import model.User;

public class UserDao extends BaseDao<User> {
    public UserDao() {
            super(User.class);
    }

}
