package meeting.room.system.dao;

import meeting.room.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
@Repository
public class UserDao extends BaseDao<User> {
    public UserDao() {
            super(User.class);
    }
}
