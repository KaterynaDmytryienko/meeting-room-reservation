package meeting.room.system.rest;

import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.security.model.UserDetails;
import meeting.room.system.service.MeetingRoomService;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/meeting_rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final MeetingRoomDao meetingRoomDao;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService, MeetingRoomDao meetingRoomDao) {
        this.meetingRoomService = meetingRoomService;
        this.meetingRoomDao = meetingRoomDao;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createMeetingRoom(Principal principal, @RequestBody MeetingRoom meetingRoom) {
        final Authentication auth = (Authentication) principal;

        if (auth != null && auth.isAuthenticated()) {
             meetingRoomService.createMeetingRoom(meetingRoom);
            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", meetingRoom.getId());
             return ResponseEntity.ok().headers(headers).body("Meeting room created successfully.");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public List<MeetingRoom> getAllMeetingRooms(Principal principal) {
        final Authentication auth = (Authentication) principal;
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        if(userDetails.getUser().hasRole("ADMIN")){
            return meetingRoomDao.getAllMeetingRooms();

        }
        return  meetingRoomDao.getAllAvailableRooms();
    }



    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole( 'ROLE_USER') || hasRole('ROLE_GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public MeetingRoom getMeetingRoomById(@PathVariable Integer id) {
        final MeetingRoom meetingRoom = meetingRoomDao.find(id);
        if (meetingRoom == null) {
            return null;
        }
        return meetingRoom;
    }



}
