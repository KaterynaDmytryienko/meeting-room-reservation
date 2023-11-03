package meeting.room.system.dao;

import meeting.room.system.model.Reservation;

public class ReservationDao extends BaseDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }
}
