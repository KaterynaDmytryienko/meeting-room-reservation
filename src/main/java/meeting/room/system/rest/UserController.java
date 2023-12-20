package meeting.room.system.rest;

import jakarta.persistence.OrderBy;
import meeting.room.system.dao.ReservationRoleDAO;
import meeting.room.system.dao.UserDao;
import meeting.room.system.dao.UserRoleDao;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.ReservationRole;
import meeting.room.system.model.User;
import meeting.room.system.model.UserRole;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import meeting.room.system.security.model.UserDetails;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserController {
    private final UserService userService;
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserDao userDao, UserRoleDao userRoleDao) {
        this.userService = userService; this.userDao = userDao;
        this.userRoleDao = userRoleDao;
    }

    /**
     * Registers a new user.
     *
     */
    @PreAuthorize("(!#user.isAdmin() && anonymous)")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> register(@RequestBody User user) {

        userService.persist(user); // Persist user first

        ReservationRole reservationRole = new ReservationRole("USER");
        UserRole userRole = new UserRole(user, reservationRole.getRoleName(), userService.getReservationRoleDAO());
        user.addUserRole(userRole);
        userRoleDao.saveUserRole(userRole);
        userDao.update(user);
        LOG.debug("User {} successfully registered.", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public User getCurrent(Authentication auth) {
        assert auth.getPrincipal() instanceof UserDetails;
        return ((UserDetails) auth.getPrincipal()).getUser();
    }

    @GetMapping(value = "/current/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public List<Reservation> getUserReservations(Authentication auth){
       return  ((UserDetails) auth.getPrincipal()).getUser().getReservations();
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Integer id){
        return userDao.find(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userDao.findAll();

    }
}
