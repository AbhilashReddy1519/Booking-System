package com.app.bs.booking_system.modules.booking_seat;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, UUID>{}
