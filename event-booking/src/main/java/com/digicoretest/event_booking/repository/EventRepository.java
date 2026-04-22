package com.digicoretest.event_booking.repository;

import com.digicoretest.event_booking.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,String> {
}
