package com.example.eventmanagementsystem.repository;

import com.example.eventmanagementsystem.model.*;

import java.util.List;
import java.util.ArrayList;

public interface SponsorRepository {
    ArrayList<Sponsor> getSponsors();

    Sponsor getSponsor(int sponsorId);

    Sponsor addSponsor(Sponsor sponsor);

    Sponsor updateSponsor(int sponsorId, Sponsor sponsor);

    void deleteSponsor(int sponsorId);

    List<Event> getSponsorEvents(int sponsorId);
}