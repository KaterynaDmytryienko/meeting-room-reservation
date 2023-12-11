package meeting.room.system.rest;

import jakarta.persistence.OrderBy;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.model.Reservation;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.service.ReservationSystemService;
import meeting.room.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/reservations")
public class ReservationController {
    private final ReservationSystemService reservationSystemService;
    private final UserService userService;
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationController(ReservationSystemService reservationSystemService, UserService userService, ReservationDao reservationDao) {
        this.reservationSystemService = reservationSystemService;
        this.userService = userService;
        this.reservationDao  =reservationDao;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(Principal principal, @RequestBody(required = false) Reservation reservation) {
        final Authentication auth = (Authentication) principal;

        if (auth != null && auth.isAuthenticated()) {
            userService.createReservation(reservation);
        }
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", reservation.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

//     Overrides class-level authorization settings
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservations(@PathVariable Integer id) {
        final Reservation reservation = reservationDao.find(id);
        if (reservation == null) {
            return null;
        }
//        assert auth.getPrincipal() instanceof UserDetails;
//        final User user = ((UserDetails) auth.getPrincipal()).getUser();
//        if (user.getRole() != Role.ADMIN && !order.getCustomer().getId().equals(user.getId())) {
//            throw new AccessDeniedException("Cannot access order of another customer");
//        }
        return reservation;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getAllReservations() {
      return reservationDao.findAll();

    }


}
