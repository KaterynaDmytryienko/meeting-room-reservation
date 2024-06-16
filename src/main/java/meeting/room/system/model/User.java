package meeting.room.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meeting.room.system.enums.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQuery(
        name = "User.banUser",
        query = "UPDATE User u SET u.isBanned = 1 WHERE u.id = :userId"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
    //    @JsonManagedReference
//    @ManyToMany
//    @JoinTable(
//            name = "user_user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_roles_id")
//    )
//    private List<UserRoles> userRoles;
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    public String getName() {
        return getUserName();
    }

    @Column(nullable = false, name = "username", unique = true)
    private String userName;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    //    @ElementCollection(targetClass = Roles.class)
//    @Enumerated(EnumType.STRING)
    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRole;

//    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startTime")
    private List<Reservation> reservations;

    private Integer isBanned = 0;

    public Integer getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Integer isBanned) {
        this.isBanned = isBanned;
    }

    public boolean isAdmin() {
         return hasRole("ADMIN");
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void erasePassword() {
        this.password = null;
    }

    public List<UserRole> getRoles() {
        return userRole;
    }

    public void setUserRoles(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public void addUserRole(UserRole toAddUserRole) {
        if (userRole == null || userRole.isEmpty()) {
            userRole = new ArrayList<>();
        }
        this.userRole.add(toAddUserRole);
    }

    public boolean hasRole(String role) {
        if(userRole==null||userRole.isEmpty())return false;
        for (UserRole userRole1 : userRole) {
            if (Objects.equals(userRole1.getReservationRole().getRoleName(), role)) return true;
        }
        return false;
    }
}
