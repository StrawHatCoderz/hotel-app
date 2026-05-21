package org.taj.hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.taj.hotel.controllers.views.HotelBookingView;
import org.taj.hotel.domain.Booking;
import org.taj.hotel.domain.Hotel;
import org.taj.hotel.exceptions.InsufficientRoomsException;
import org.taj.hotel.exceptions.InvalidCityNameException;
import org.taj.hotel.exceptions.InvalidRoomIdException;
import org.taj.hotel.exceptions.NoBookingsException;
import org.taj.hotel.service.BookingService;
import org.taj.hotel.service.HotelService;
import org.taj.hotel.view.ApiError;
import org.taj.hotel.view.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AppController {
  private final HotelService hotelService;
  private final BookingService bookingService;

  public AppController(HotelService hotelService, BookingService bookingService) {
    this.hotelService = hotelService;
    this.bookingService = bookingService;
  }

  @GetMapping("search/hotels")
  public ResponseEntity<ApiResponse<?>> searchHotels(@RequestParam String city) {
    try {
      List<Hotel> hotels = hotelService.findHotelsByCity(city);
      return ResponseEntity.ok(ApiResponse.success(hotels));
    } catch (InvalidCityNameException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(new ApiError(e.getMessage())));
    }
  }

  @GetMapping("bookings")
  public ResponseEntity<ApiResponse<?>> serveBookings() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUser = authentication.getName();
    try {
      List<Booking> hotels = bookingService.findBookingsOf(currentUser);
      return ResponseEntity.ok(ApiResponse.success(hotels));
    } catch (NoBookingsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(new ApiError(e.getMessage())));
    }
  }

  @PostMapping("bookings")
  public ResponseEntity<ApiResponse<?>> bookHotel(@RequestBody HotelBookingView hotelBookingView) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUser = authentication.getName();
    try {
      bookingService.bookRoom(currentUser, hotelBookingView.hotelId(),
              hotelBookingView.noOfRooms());
    } catch (InsufficientRoomsException | InvalidRoomIdException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(new ApiError(e.getMessage())));
    }
    return ResponseEntity.ok(ApiResponse.success("Room Booked Successfully"));
  }
}
