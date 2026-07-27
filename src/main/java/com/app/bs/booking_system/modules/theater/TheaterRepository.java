package com.app.bs.booking_system.modules.theater;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
  
}
