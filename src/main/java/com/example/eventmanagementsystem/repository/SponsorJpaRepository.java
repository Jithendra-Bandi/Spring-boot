package com.example.eventmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagementsystem.model.Sponsor;

@Repository
public interface SponsorJpaRepository extends JpaRepository<Sponsor, Integer> {

}