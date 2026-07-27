package com.app.bs.booking_system.modules.screens;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, UUID>{}
