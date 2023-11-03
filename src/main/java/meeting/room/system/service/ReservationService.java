package meeting.room.system.service;

import meeting.room.system.dao.ReservationDao;
import meeting.room.system.dao.UserDao;

public class ReservationService {
private final ReservationDao reservationDao;
private final UserDao userDao;
    public ReservationService(ReservationDao reservationDao, UserDao userDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
    }

}
