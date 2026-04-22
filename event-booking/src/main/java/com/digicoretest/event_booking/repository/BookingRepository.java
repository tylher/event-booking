package com.digicoretest.event_booking.repository;

import com.digicoretest.event_booking.model.Booking;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {
    boolean existsByEvent_IdAndAttendeeEmail(String eventId, String attendeeEmail);

    Page<Booking> findByEvent_Id(String eventId, Pageable pageable);
}
