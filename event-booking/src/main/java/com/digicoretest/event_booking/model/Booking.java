package com.digicoretest.event_booking.model;

import com.digicoretest.event_booking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String attendeeName;

    private String attendeeEmail;

    private LocalDateTime bookedAt;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "eventId",referencedColumnName = "id")
    private Event event;
}
