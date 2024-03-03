package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventJpaService implements EventRepository {
	@Autowired
	public EventJpaRepository eventJpaRepository;

	@Autowired
	public SponsorJpaRepository sponsorJpaRepository;

	@Override
	public ArrayList<Event> getEvents() {
		List<Event> eventList = eventJpaRepository.findAll();
		ArrayList<Event> events = new ArrayList<>(eventList);
		return events;
	}

	@Override
	public Event getEvent(int eventId) {
		try {
			Event event = eventJpaRepository.findById(eventId).get();
			return event;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Event addEvent(Event event) {
		try {
			List<Integer> sponsorIds = new ArrayList<>();
			for (Sponsor sponsor : event.getSponsors()) {
				sponsorIds.add(sponsor.getSponsorId());
			}
			List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
			if (sponsors.size() != sponsorIds.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			event.setSponsors(sponsors);
			eventJpaRepository.save(event);
			return event;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Event updateEvent(int eventId, Event event) {
		try {
			Event newEvent = eventJpaRepository.findById(eventId).get();
			if (event.getEventName() != null) {
				newEvent.setEventName(event.getEventName());
			}
			if (event.getDate() != null) {
				newEvent.setDate(event.getDate());
			}
			if (event.getSponsors() != null) {
				List<Integer> sponsorIds = new ArrayList<>();
				for (Sponsor sponsor : event.getSponsors()) {
					sponsorIds.add(sponsor.getSponsorId());
				}
				List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
				if (sponsors.size() != sponsorIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				newEvent.setSponsors(sponsors);
			}
			eventJpaRepository.save(newEvent);
			return newEvent;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteEvent(int eventId) {
		try {
			Event event = eventJpaRepository.findById(eventId).get();
			List<Sponsor> sponsors = event.getSponsors();
			for (Sponsor sponsor : sponsors) {
				sponsor.getEvents().remove(event);
			}
			sponsorJpaRepository.saveAll(sponsors);
			eventJpaRepository.deleteById(eventId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Sponsor> getEventSponsors(int eventId) {
		try {
			Event event = eventJpaRepository.findById(eventId).get();
			return event.getSponsors();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}