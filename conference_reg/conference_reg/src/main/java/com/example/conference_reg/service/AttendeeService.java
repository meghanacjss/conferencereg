package com.example.conference_reg.service;
import com.example.conference_reg.entity.Attendee;
import com.example.conference_reg.exception.UserNotFoundException;
import com.example.conference_reg.model.AttendeeModel;
import com.example.conference_reg.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public AttendeeModel createAttendee(Attendee attendee) {
        Attendee savedAttendee = attendeeRepository.save(attendee);
        return convertToModel(savedAttendee);
    }

    public List<AttendeeModel> getAllAttendees() {
        List<Attendee> attendees = attendeeRepository.findAll();
        return attendees.stream()
                .map(this::convertToModel)
                .sorted(Comparator.comparingInt(AttendeeModel::getAid))
                .collect(Collectors.toList());
    }

    public Optional<AttendeeModel> getAttendeeById(int aid) throws UserNotFoundException {
        Optional<Attendee> optionalAttendee = attendeeRepository.findById(aid);
        return optionalAttendee.map(this::convertToModel);
    }

    private AttendeeModel convertToModel(Attendee attendee) {
        AttendeeModel attendeeModel = new AttendeeModel();
        attendeeModel.setAid(attendee.getAid());
        attendeeModel.setAname(attendee.getAname());
        attendeeModel.setEmail(attendee.getEmail());
        attendeeModel.setAffiliation(attendee.getAffiliation());

        return attendeeModel;
    }
}
