package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;

@Service
public class SponsorJpaService implements SponsorRepository {
	@Autowired
	public EventJpaRepository eventJpaRepository;

	@Autowired
	public SponsorJpaRepository sponsorJpaRepository;

	@Override
	public ArrayList<Sponsor> getSponsors() {
		List<Sponsor> sponsorList = sponsorJpaRepository.findAll();
		ArrayList<Sponsor> sponsors = new ArrayList<>(sponsorList);
		return sponsors;
	}

	@Override
	public Sponsor getSponsor(int sponsorId) {
		try {
			Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
			return sponsor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Sponsor addSponsor(Sponsor sponsor) {
		try {
			List<Integer> eventIds = new ArrayList<>();
			for (Event event : sponsor.getEvents()) {
				eventIds.add(event.getEventId());
			}
			List<Event> events = eventJpaRepository.findAllById(eventIds);
			if (events.size() != eventIds.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			sponsor.setEvents(events);
			sponsorJpaRepository.save(sponsor);
			return sponsor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
		try {
			Sponsor newSponsor = sponsorJpaRepository.findById(sponsorId).get();
			if (sponsor.getSponsorName() != null) {
				newSponsor.setSponsorName(sponsor.getSponsorName());
			}
			if (sponsor.getIndustry() != null) {
				newSponsor.setIndustry(sponsor.getIndustry());
			}
			if (sponsor.getEvents() != null) {
				try {
					List<Integer> eventIds = new ArrayList<>();
					for (Event event : sponsor.getEvents()) {
						eventIds.add(event.getEventId());
					}
					List<Event> events = eventJpaRepository.findAllById(eventIds);
					if (events.size() != eventIds.size()) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
					}
					sponsor.setEvents(events);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
			}
			sponsorJpaRepository.save(newSponsor);
			return newSponsor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteSponsor(int sponsorId) {
		try {
			Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
			List<Event> events = sponsor.getEvents();
			for (Event event : events) {
				event.getSponsors().remove(sponsor);
			}
			eventJpaRepository.saveAll(events);
			sponsorJpaRepository.deleteById(sponsorId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Event> getSponsorEvents(int sponsorId) {
		try {
			Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
			return sponsor.getEvents();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}