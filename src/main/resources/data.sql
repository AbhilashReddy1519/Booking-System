-- =====================================================================
-- Seed data for booking-system
-- Place at: src/main/resources/data.sql
-- Requires: spring.jpa.defer-datasource-initialization=true
--           (so Hibernate creates tables BEFORE this script runs)
--
-- Safe to re-run: uses fixed UUIDs + ON CONFLICT DO NOTHING, so restarting
-- the app repeatedly will not create duplicate rows.
-- =====================================================================

-- ---------------------------------------------------------------------
-- LANGUAGES
-- ---------------------------------------------------------------------
INSERT INTO languages (id, language) VALUES
  ('a0000000-0000-0000-0000-000000000001', 'English'),
  ('a0000000-0000-0000-0000-000000000002', 'Hindi'),
  ('a0000000-0000-0000-0000-000000000003', 'Tamil'),
  ('a0000000-0000-0000-0000-000000000004', 'Telugu'),
  ('a0000000-0000-0000-0000-000000000005', 'Korean')
ON CONFLICT (id) DO NOTHING;

-- ---------------------------------------------------------------------
-- THEATERS
-- ---------------------------------------------------------------------
INSERT INTO theaters (id, name, address) VALUES
  ('b0000000-0000-0000-0000-000000000001', 'Cinepolis - Riverside Galleria', '221 Riverside Boulevard, Somandepalli'),
  ('b0000000-0000-0000-0000-000000000002', 'PVR Cinemas - Crystal Mall',      '48 MG Road, Somandepalli'),
  ('b0000000-0000-0000-0000-000000000003', 'INOX - Skyline Central',         '7 Skyline Avenue, Somandepalli')
ON CONFLICT (id) DO NOTHING;

-- ---------------------------------------------------------------------
-- SCREENS
-- Two per theater: one standard hall, one premium/large-format hall.
-- ---------------------------------------------------------------------
INSERT INTO screens (id, name, theater_id) VALUES
  -- Cinepolis - Riverside Galleria
  ('c0000000-0000-0000-0000-000000000001', 'Screen 1',              'b0000000-0000-0000-0000-000000000001'),
  ('c0000000-0000-0000-0000-000000000002', 'IMAX Screen',           'b0000000-0000-0000-0000-000000000001'),

  -- PVR Cinemas - Crystal Mall
  ('c0000000-0000-0000-0000-000000000003', 'Screen 1',              'b0000000-0000-0000-0000-000000000002'),
  ('c0000000-0000-0000-0000-000000000004', 'Screen 2 - Dolby Atmos','b0000000-0000-0000-0000-000000000002'),

  -- INOX - Skyline Central
  ('c0000000-0000-0000-0000-000000000005', 'Screen 1',              'b0000000-0000-0000-0000-000000000003'),
  ('c0000000-0000-0000-0000-000000000006', 'Gold Screen',           'b0000000-0000-0000-0000-000000000003')
ON CONFLICT (id) DO NOTHING;