package meeting.room.system.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.ReservationRoleDAO;
import meeting.room.system.dao.UserDao;
import meeting.room.system.model.Reservation;

import meeting.room.system.enums.Roles;
import meeting.room.system.model.User;
import meeting.room.system.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    private ReservationSystemService reservationSystemService;
    private ReservationRoleDAO reservationRoleDAO;
    private UserDao userDao;
    private ReservationDao reservationDao;


    private final PasswordEncoder passwordEncoder;

    public ReservationRoleDAO getReservationRoleDAO() {
        return reservationRoleDAO;
    }

    public UserService(ReservationSystemService reservationSystemService, PasswordEncoder passwordEncoder,
                       ReservationRoleDAO reservationRoleDAO, UserDao userDao){
        this.passwordEncoder = passwordEncoder;
        this.reservationSystemService = reservationSystemService;
        this.reservationRoleDAO = reservationRoleDAO;
        this.userDao = userDao;
    }

//    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
//        if (user.getRoles() == null) {
//            user.setRoles(new ArrayList<>());
//            user.setRoles(Collections.singletonList(Roles.USER));
//        }
        if (user.getRoles() == null) {
            user.setUserRoles(new ArrayList<>());
        }
        if (!user.hasRole("USER")) {
            user.getRoles().add(new UserRole(user, "USER", reservationRoleDAO));
        }
        userDao.persist(user);
    }




    public List<Reservation> getUserReservations(Reservation reservation) {
        return reservation.getUser().getReservations();
    }
}
