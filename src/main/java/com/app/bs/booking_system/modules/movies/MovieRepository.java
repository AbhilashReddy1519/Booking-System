package com.app.bs.booking_system.modules.movies;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, UUID>{
}
