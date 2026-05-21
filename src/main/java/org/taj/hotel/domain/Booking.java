package org.taj.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Booking(
        @Id
        String bookingId, String hotelId, String userId,
                      int totalRoomsBooked) {
}
