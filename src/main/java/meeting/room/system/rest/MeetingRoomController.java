package meeting.room.system.rest;

import meeting.room.system.dao.MeetingRoomDao;
import meeting.room.system.dao.ReservationDao;
import meeting.room.system.model.MeetingRoom;
import meeting.room.system.model.Reservation;
import meeting.room.system.rest.util.RestUtils;
import meeting.room.system.service.MeetingRoomService;
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
@RequestMapping("/rest/meeting_rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;
    private final MeetingRoomDao meetingRoomDao;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService, UserService userService, MeetingRoomDao meetingRoomDao) {
        this.meetingRoomService = meetingRoomService;
        this.userService = userService;
        this.meetingRoomDao = meetingRoomDao;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
//        final Authentication auth = (Authentication) principal;

        //if (auth != null && auth.isAuthenticated()) {
           final MeetingRoom mr = meetingRoomService.create(meetingRoom);
       // }
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", mr.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MeetingRoom getMeetingRoom(@PathVariable Integer id) {
        final MeetingRoom meetingRoom = meetingRoomDao.find(id);
        if (meetingRoom == null) {
            return null;
        }
//        assert auth.getPrincipal() instanceof UserDetails;
//        if (user.getRole() != Role.ADMIN && !order.getCustomer().getId().equals(user.getId())) {
//            throw new AccessDeniedException("Cannot access order of another customer");
//        }
        return meetingRoom;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomDao.findAll();

    }
}
