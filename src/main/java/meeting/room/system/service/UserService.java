package meeting.room.system.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.UserDao;
import meeting.room.system.dao.UserRolesDao;
import meeting.room.system.model.Reservation;

import meeting.room.system.model.Roles;
import meeting.room.system.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    ReservationSystemService reservationSystemService;

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
            log.warn("Not reserved.");
        }
    }

    public List<Reservation> getUserReservations(Reservation reservation) {
        return reservation.getUser().getReservations();
    }
}
