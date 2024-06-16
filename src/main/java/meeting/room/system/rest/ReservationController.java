package meeting.room.system.rest;

import meeting.room.system.dao.ReservationDao;
import meeting.room.system.model.Reservation;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.security.model.UserDetails;
import meeting.room.system.service.ReservationSystemService;
import meeting.room.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

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

@PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createReservation(Principal principal, @RequestBody Reservation reservation) {
        final Authentication auth = (Authentication) principal;

        if (auth != null && auth.isAuthenticated()) {
            boolean isApproved = reservationSystemService.createReservation(reservation, ((UserDetails) auth.getPrincipal()).getUser());
            if (isApproved) {
                final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", reservation.getId());
                return ResponseEntity.ok().headers(headers).body("Reservation approved and created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation not approved.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservationByReservationId(Authentication auth, @PathVariable Integer id) throws AccessDeniedException {
        final Reservation reservation = reservationDao.getByReservationId(id);
        if (reservation == null) {
            return null;
        }
        assert auth.getPrincipal() instanceof UserDetails;
        return reservation;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getAllReservations() {
      return reservationDao.getAllReservations();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/userid/{searchedUserid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservationsByUserId(@PathVariable Integer searchedUserid){
        return reservationDao.getAllByUserId(searchedUserid);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/roomid/{meetingRoomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation>getAllReservationsByMeetingRoomId(@PathVariable Integer meetingRoomId){
        return reservationDao.getAllReservationsForMeetingRoom(meetingRoomId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_USER')")
    @PostMapping(value="/cancel/reservationId/{reservationId}")
    @Transactional
    public void cancelReservation(Principal principal, @PathVariable Integer reservationId){
        final Authentication auth = (Authentication) principal;
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        if(userDetails.getUser().getIsBanned()!=1 && reservationDao.find(reservationId)!=null){
            Reservation reservation = userDetails.getUser().getReservations()
                    .stream()
                    .filter(reservation1 -> Objects.equals(reservation1.getId(), reservationId))
                    .findFirst()
                    .orElseThrow();
            reservationSystemService.cancelReservation(reservation);
        }

    }
}
