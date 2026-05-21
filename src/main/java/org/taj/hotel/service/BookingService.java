package org.taj.hotel.service;

import org.springframework.stereotype.Service;
import org.taj.hotel.domain.Booking;
import org.taj.hotel.domain.Hotel;
import org.taj.hotel.exceptions.InsufficientRoomsException;
import org.taj.hotel.exceptions.InvalidRoomIdException;
import org.taj.hotel.exceptions.NoBookingsException;
import org.taj.hotel.repositry.BookingRepo;
import org.taj.hotel.repositry.HotelRepo;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
  private final BookingRepo bookingRepo;
  private final HotelRepo hotelRepo;

  public BookingService(BookingRepo bookingRepo, HotelRepo hotelRepo) {
    this.bookingRepo = bookingRepo;
    this.hotelRepo = hotelRepo;
  }

  public List<Booking> findBookingsOf(String currentUserId) throws NoBookingsException {
    List<Booking> bookings = bookingRepo.findByUserId(currentUserId);
    if (bookings.isEmpty()) {
      throw new NoBookingsException();
    }
    return bookings;
  }

  public void bookRoom(String currentUser, String hotelId, int noOfRooms) throws InsufficientRoomsException, InvalidRoomIdException {

    Hotel hotel = hotelRepo.findHotelById(hotelId);
    if (hotel == null) {
      throw new InvalidRoomIdException();
    }

    if (hotel.canBook(noOfRooms)) {
      throw new InsufficientRoomsException();
    }

    hotel.setAvailableRooms(noOfRooms);
    hotelRepo.save(hotel);

    String id = UUID.randomUUID().toString();

    bookingRepo.save(new Booking(id,hotelId, currentUser, noOfRooms));
  }
}
