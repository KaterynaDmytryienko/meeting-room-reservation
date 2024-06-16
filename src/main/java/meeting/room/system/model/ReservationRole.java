package meeting.room.system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_role")
public class ReservationRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String role_name;

    public ReservationRole(String name) {
        this.role_name = name;
    }

    public ReservationRole() {

    }

    public String getRoleName() {
        return role_name;
    }

    public void setName(String name) {
        this.role_name = name;
    }
}

