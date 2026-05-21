package org.taj.hotel.service;

import org.springframework.stereotype.Service;
import org.taj.hotel.domain.Booking;
import org.taj.hotel.exceptions.NoBookingsException;
import org.taj.hotel.repositry.BookingRepo;

import java.util.List;

@Service
public class BookingService {
  private final BookingRepo bookingRepo;

  public BookingService(BookingRepo bookingRepo) {
    this.bookingRepo = bookingRepo;
  }

  public List<Booking> findBookingsOf(String currentUserId) throws NoBookingsException {
    List<Booking> bookings = bookingRepo.findByUserId(currentUserId);
    if (bookings.isEmpty()) {
      throw new NoBookingsException();
    }
    return bookings;
  }
}
