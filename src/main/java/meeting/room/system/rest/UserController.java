package meeting.room.system.rest;

import jakarta.persistence.OrderBy;
import meeting.room.system.dao.UserDao;
import meeting.room.system.model.Reservation;
import meeting.room.system.model.User;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserController {
    private final UserService userService;
    private final UserDao userDao;

    @Autowired
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService; this.userDao = userDao;
    }


    /**
     * Registers a new user.
     *
     */
//    @PreAuthorize("(!#user.isAdmin() && anonymous) || hasRole('ROLE_ADMIN')")
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> register(@RequestBody User user) {
//        userService.persist(user);
//        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
//    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
//    public User getCurrent(Authentication auth) {
//        assert auth.getPrincipal() instanceof UserDetails;
//        return ((UserDetails) auth.getPrincipal()).getUser();
//    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Integer id){
        return userDao.find(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userDao.findAll();

    }
}
