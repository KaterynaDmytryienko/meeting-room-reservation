package meeting.room.system.dao;

import jakarta.persistence.NoResultException;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import meeting.room.system.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDao extends BaseDao<User> {

    @Transactional
    public void banUser(int userId) {
//        em.createQuery("UPDATE User u SET u.isBanned = 1 WHERE u.id = :userId")
//                .setParameter("userId", userId)
//                .executeUpdate();

        em.createNamedQuery("User.banUser")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public UserDao() {
            super(User.class);
    }
    @Transactional
    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
@Transactional
    public List<UserRole> findUserRolesByUserId(Integer userId) {
        return em.createQuery("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId", UserRole.class)
                .setParameter("userId", userId)
                .getResultList();
    }
@Transactional
    public List<Reservation> findReservationsByUserId(Integer userId) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.user.id = :userId", Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
    }


}
