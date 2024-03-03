package com.example.eventmanagementsystem.repository;

import com.example.eventmanagementsystem.model.*;

import java.util.List;
import java.util.ArrayList;

public interface EventRepository {
    ArrayList<Event> getEvents();

    Event getEvent(int eventId);

    Event addEvent(Event event);

    Event updateEvent(int eventId, Event event);

    void deleteEvent(int eventId);

    List<Sponsor> getEventSponsors(int eventId);
}