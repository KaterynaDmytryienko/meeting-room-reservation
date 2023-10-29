package dao;

import model.Reservation;
import model.User;

public class ReservationDao extends BaseDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }
}
