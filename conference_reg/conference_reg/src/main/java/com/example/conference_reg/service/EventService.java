package com.example.conference_reg.service;

import com.example.conference_reg.entity.Event;
import com.example.conference_reg.model.EventModel;
import com.example.conference_reg.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class EventService {

@Autowired
    private EventRepository eventRepository;

    public EventModel add(EventModel eventModel) {
        Event event = convertToEntity(eventModel);
        return convertToModel(eventRepository.save(event));
    }

    public List<EventModel> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToModel)
                .sorted(Comparator.comparingInt(EventModel::getEid))
                .toList();
               // .collect(Collectors.toList());
    }

    public EventModel updateEvent(EventModel eventModel) {
        Event event = convertToEntity(eventModel);
        Event updateEvent = eventRepository.findById(event.getEid()).orElse(null);
        if (updateEvent != null) {
            updateEvent.setEname(event.getEname());
            updateEvent.setDate(event.getDate());
            updateEvent.setVenue(event.getVenue());
            eventRepository.save(updateEvent);
            return convertToModel(updateEvent);
        }
        return null;
    }
    public EventModel getEventById(int eid) {
        Event event = eventRepository.findById(eid).orElse(null);
        return convertToModel(event);
    }

    private EventModel convertToModel(Event event) {
        if (event == null) {
            return null;
        }
        EventModel eventModel = new EventModel();
        eventModel.setEid(event.getEid());
        eventModel.setEname(event.getEname());
        eventModel.setDate(event.getDate());
        eventModel.setVenue(event.getVenue());
        return eventModel;
    }

    private Event convertToEntity(EventModel eventModel) {
        if (eventModel == null) {
            return null;
        }
        Event event = new Event();
        event.setEid(eventModel.getEid());
        event.setEname(eventModel.getEname());
        event.setDate(eventModel.getDate());
        event.setVenue(eventModel.getVenue());
        return event;
    }
}
