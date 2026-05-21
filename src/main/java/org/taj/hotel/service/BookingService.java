package org.taj.hotel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.taj.hotel.domain.Booking;
import org.taj.hotel.domain.Hotel;
import org.taj.hotel.domain.User;
import org.taj.hotel.exceptions.InsufficientRoomsException;
import org.taj.hotel.exceptions.InvalidRoomIdException;
import org.taj.hotel.exceptions.NoBookingsException;
import org.taj.hotel.repositry.BookingRepo;
import org.taj.hotel.repositry.HotelRepo;
import org.taj.hotel.repositry.UserRepo;
import org.taj.hotel.view.BookingView;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
  private static final Logger log = LoggerFactory.getLogger(BookingService.class);
  private final BookingRepo bookingRepo;
  private final HotelRepo hotelRepo;
  private final UserRepo userRepo;

  public BookingService(BookingRepo bookingRepo, HotelRepo hotelRepo, UserRepo userRepo) {
    this.bookingRepo = bookingRepo;
    this.hotelRepo = hotelRepo;
    this.userRepo = userRepo;
  }

  public List<Booking> findBookingsOf(String currentUserId) throws NoBookingsException {
    List<Booking> bookings = bookingRepo.findByUserId(currentUserId);
    if (bookings.isEmpty()) {
      throw new NoBookingsException();
    }
    return bookings;
  }

  public void bookRoom(String currentUser, String hotelId, int noOfRooms) throws InsufficientRoomsException, InvalidRoomIdException {
    Hotel hotel = hotelRepo.findHotelByHotelId(hotelId);
    if (hotel == null) {
      throw new InvalidRoomIdException();
    }

    if (!hotel.canBook(noOfRooms)) {
      throw new InsufficientRoomsException();
    }

    hotel.setAvailableRooms(noOfRooms);
    hotelRepo.save(hotel);

    String id = UUID.randomUUID().toString();

    bookingRepo.save(new Booking(id, hotelId, currentUser, noOfRooms));
  }

  public BookingView toJSON(String bookingId) {
    Booking booking = bookingRepo.findBookingByBookingId(bookingId);
    Hotel hotel = hotelRepo.findHotelByHotelId(booking.hotelId());
    User user = userRepo.findByUsername(booking.userId());
    return new BookingView(user.getUsername(), hotel.getName());
  }
}
