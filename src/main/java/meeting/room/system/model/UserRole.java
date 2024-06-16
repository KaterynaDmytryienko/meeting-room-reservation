package meeting.room.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import meeting.room.system.dao.ReservationRoleDAO;

@Entity
    @Table(name = "user_role")
    public class UserRole {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Getter
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;

        @Getter
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "role_id")
        private ReservationRole reservationRole;

    public UserRole() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReservationRole(ReservationRole reservationRole) {
        this.reservationRole = reservationRole;
    }

    public UserRole(User user, String role, ReservationRoleDAO reservationRoleDAO){
        setUser(user);
//        ReservationRole reservationRole1=new ReservationRole(role);
//        setReservationRole(reservationRole1);

        ReservationRole reservationRole = reservationRoleDAO.findByName(role);
        if (reservationRole == null) {
            reservationRole = new ReservationRole(role);
            reservationRoleDAO.saveReservationRole(reservationRole);
        }
        setReservationRole(reservationRole);
    }
}

