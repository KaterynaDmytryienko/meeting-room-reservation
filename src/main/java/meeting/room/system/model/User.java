package meeting.room.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meeting.room.system.enums.Roles;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity{
    @ManyToMany
    @JoinTable(
            name = "user_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_roles_id")
    )
    private List<UserRoles> userRoles;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Date dateOfBirth;
    private List<Roles> roles;
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
    private boolean isBanned = false;
}
