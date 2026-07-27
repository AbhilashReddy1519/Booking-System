package com.app.bs.booking_system.modules.shows;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, UUID>{}
