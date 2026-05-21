package org.taj.hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taj.hotel.domain.Hotel;
import org.taj.hotel.exceptions.InvalidCityNameException;
import org.taj.hotel.service.HotelService;
import org.taj.hotel.views.ApiError;
import org.taj.hotel.views.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AppController {
  private final HotelService hotelService;

  public AppController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping("/search/hotels")
  public ResponseEntity<ApiResponse<?>> searchHotels(@RequestParam String city) {
    try {
      List<Hotel> hotels = hotelService.findHotelsByCity(city);
      return ResponseEntity.ok(ApiResponse.success(hotels));
    } catch (InvalidCityNameException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(new ApiError(e.getMessage())));
    }

  }
}
