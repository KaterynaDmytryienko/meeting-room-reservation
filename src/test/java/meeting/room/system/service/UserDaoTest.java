package meeting.room.system.service;

import meeting.room.system.dao.UserDao;
import meeting.room.system.dao.UserRoleDao;
import meeting.room.system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Test
    @Transactional
    public void findUserByUsernameTest() {
        assertNotNull(userDao.findByUsername("merlin1a"));
    }

    @Test
    @Transactional
    public void userRemovalTest() {
        User user = userDao.findByUsername("merlin1a");
        Integer id = userDao.findByUsername("merlin1a").getId();
        userDao.remove(user);
        assertEquals(userDao.findByUsername("merlin1a")==null, true);
        assertTrue(userDao.findUserRolesByUserId(id).isEmpty());
        assertTrue(userDao.findReservationsByUserId(id).isEmpty());
    }
}
