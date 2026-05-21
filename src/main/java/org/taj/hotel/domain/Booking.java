package org.taj.hotel.domain;

public record Booking(String bookingId, String hotelId, String userId,
                      int totalRoomsBooked) {
}
