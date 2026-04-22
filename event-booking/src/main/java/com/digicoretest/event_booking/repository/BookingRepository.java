package com.digicoretest.event_booking.repository;

import com.digicoretest.event_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {
}
