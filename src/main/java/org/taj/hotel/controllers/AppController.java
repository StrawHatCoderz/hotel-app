package org.taj.hotel.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class AppController {
  @GetMapping("/search/hotels")
  public ResponseEntity<?> searchHotels(@RequestParam String city) {
    return ResponseEntity.ok(city);
  }
}
