package meeting.room.system.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meeting.room.system.dao.UserDao;
import meeting.room.system.model.Reservation;

import meeting.room.system.enums.Roles;
import meeting.room.system.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private ReservationSystemService reservationSystemService;


    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
            user.setRoles(Collections.singletonList(Roles.USER));
        }
    }

    @Transactional
    public void createReservation(Reservation reservation) {
        Objects.requireNonNull(reservation);

        if (reservationSystemService.approveReservation(reservation)) {
            reservation.setStatus("Reserved");
            reservation.getMeetingRoom().setAvailable(false);

        } else {
            log.warn("Not reserved");
        }
    }

    public List<Reservation> getUserReservations(Reservation reservation) {
        return reservation.getUser().getReservations();
    }
}
