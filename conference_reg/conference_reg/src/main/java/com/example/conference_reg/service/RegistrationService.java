package com.example.conference_reg.service;

import com.example.conference_reg.entity.Attendee;
import com.example.conference_reg.entity.Event;
import com.example.conference_reg.entity.Registration;
import com.example.conference_reg.model.RegistrationModel;
import com.example.conference_reg.repository.RegistrationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
@Autowired
private RegistrationRepository registrationRepository;
    public RegistrationModel createRegistration(@Valid RegistrationModel registrationModel, Event event, Attendee attendee) {
        Registration registration = convertToEntity(registrationModel);
        registration.setEvent(event);
        registration.setAttendee(attendee);
        return convertToModel(registrationRepository.save(registration));
    }

    public List<RegistrationModel> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(this::convertToModel)
                .sorted(Comparator.comparing(RegistrationModel::getRdate))
                .collect(Collectors.toList());
    }

    public List<RegistrationModel> getRegistrationsByEvent(Event event) {
        List<Registration> registrations = registrationRepository.findByEvent(event);
        return registrations.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public List<RegistrationModel> getRegistrationsByAttendee(Attendee attendee) {
        List<Registration> registrations = registrationRepository.findByAttendee(attendee);
        return registrations.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public RegistrationModel getRegistrationById(int rid) {
        Registration registration = registrationRepository.findById(rid).orElse(null);
        if (registration != null) {
            return convertToModel(registration);
        }
        return null;
    }
//    public RegistrationModel cancelRegistration(int rid) {
//        Registration registrationEntity = registrationRepository.findById(rid).orElse(null);
//
//        if (registrationEntity != null) {
//            RegistrationModel registrationModel = convertToModel(registrationEntity);
//
//            registrationRepository.delete(registrationEntity);
//
//            return registrationModel;
//        } else {
//            throw new IllegalArgumentException("Registration not found for ID: " + rid);
//        }
//    }
    public RegistrationModel convertToModel(Registration registration) {
        RegistrationModel registrationModel = new RegistrationModel();
        registrationModel.setRid(registration.getRid());
        registrationModel.setRdate(registration.getRdate());
        registrationModel.setRamount(registration.getRamount());
        registrationModel.setAttendee(registration.getAttendee());
        registrationModel.setEvent(registration.getEvent());
        return registrationModel;
    }

    public  Registration convertToEntity(RegistrationModel registrationModel) {
        Registration registration = new Registration();
        registration.setRid(registrationModel.getRid());
        registration.setRdate(registrationModel.getRdate());
        registration.setRamount(registrationModel.getRamount());
        registration.setAttendee(registrationModel.getAttendee());
        registration.setEvent(registrationModel.getEvent());
        return registration;
    }
}
