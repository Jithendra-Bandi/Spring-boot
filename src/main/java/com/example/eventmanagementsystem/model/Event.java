package com.example.eventmanagementsystem.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int eventId;

	@Column(name = "name")
	private String eventName;

	@Column(name = "date")
	private String date;

	@ManyToMany
	@JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "eventid"), inverseJoinColumns = @JoinColumn(name = "sponsorid"))
	@JsonIgnoreProperties("events")
	private List<Sponsor> sponsors = new ArrayList<>();

	public Event() {
	}

	public Event(int eventId, String eventName, String date, List<Sponsor> sponsors) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.date = date;
		this.sponsors = sponsors;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

}