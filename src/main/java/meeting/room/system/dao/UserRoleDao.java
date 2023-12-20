package meeting.room.system.dao;

import jakarta.transaction.Transactional;
import meeting.room.system.model.ReservationRole;
import meeting.room.system.model.User;
import meeting.room.system.model.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDao extends BaseDao<UserRole> {
    public UserRoleDao() {
        super(UserRole.class);
    }
    @Transactional
    public void saveUserRole(UserRole userRole) {
        persist(userRole);
    }

//    @Transactional
//    public ReservationRole getUserRole(User user){
//        return em.createQuery("SELECT r from UserRole r WHERE r. = :id", ReservationRole.class)
//                .setParameter("id", id)
//                .getSingleResult();
//    }
}
