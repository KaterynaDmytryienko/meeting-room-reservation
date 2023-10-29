package model;

import java.util.List;

public class ReservationSystem {
    List<MeetingRoom> meetingRooms;
List<Reservation> reservations;
public void cancelReservation(){

}
public void approveReservation(){

}

    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
