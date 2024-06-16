package meeting.room.system.dao;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import meeting.room.system.model.ReservationRole;
import meeting.room.system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRoleDAO extends BaseDao<ReservationRole> {
    public ReservationRoleDAO() {
        super(ReservationRole.class);
    }


    @Transactional
    public void saveReservationRole(ReservationRole reservationRole) {
        persist(reservationRole);
    }

    @Transactional
    public ReservationRole findByName(String name) {
        TypedQuery<ReservationRole> query = em.createQuery(
                "SELECT r FROM ReservationRole r WHERE r.role_name = :name", ReservationRole.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            // Handle or log exception as needed, e.g., NoResultException when no result is found
            return null;
        }
    }
}
