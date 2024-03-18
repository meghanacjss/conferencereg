package com.example.conference_reg.controller;

import com.example.conference_reg.entity.Attendee;
import com.example.conference_reg.entity.Event;
import com.example.conference_reg.exception.UserNotFoundException;
import com.example.conference_reg.model.AttendeeModel;
import com.example.conference_reg.model.EventModel;
import com.example.conference_reg.service.AttendeeService;
import com.example.conference_reg.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class AttendeeController {
@Autowired
  private AttendeeService attendeeService;
@Autowired
   private  EventService eventService;
       @PostMapping("/createattendee/")
    public ResponseEntity<AttendeeModel> createAttendee(@Valid @RequestBody AttendeeModel attendeeModel,
                                                        @RequestParam int eid) {
           EventModel event = eventService.getEventById(eid);
           if (event == null) {
               return ResponseEntity.notFound().build();
           }
           Attendee attendee = convertToEntity(attendeeModel);
           attendee.setEvent(convertToEntity(event));
           AttendeeModel createdAttendee = attendeeService.createAttendee(attendee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendee);
    }

    @GetMapping("/allattendees")
    public ResponseEntity<List<AttendeeModel>> getAllAttendees() {
        List<AttendeeModel> attendees = attendeeService.getAllAttendees();
        return ResponseEntity.ok(attendees);
    }

    @GetMapping("/{aid}")
    public ResponseEntity<AttendeeModel> getAttendeeById(@PathVariable int aid) throws UserNotFoundException{
        try {
            Optional<AttendeeModel> attendee = attendeeService.getAttendeeById(aid);
            return attendee.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public Attendee convertToEntity(AttendeeModel attendeeModel) {

        Attendee attendee = new Attendee();
        attendee.setAid(attendeeModel.getAid());
        attendee.setAname(attendeeModel.getAname());
        attendee.setEmail(attendeeModel.getEmail());
        attendee.setAffiliation(attendeeModel.getAffiliation());
        return attendee;
    }
    public Event convertToEntity(EventModel eventModel) {
        Event event = new Event();
        event.setEid(eventModel.getEid());
        event.setEname(eventModel.getEname());
        event.setDate(eventModel.getDate());
        event.setVenue(eventModel.getVenue());
        return event;
    }
}
