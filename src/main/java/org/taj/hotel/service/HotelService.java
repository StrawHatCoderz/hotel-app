package org.taj.hotel.service;

import org.springframework.stereotype.Service;
import org.taj.hotel.domain.Hotel;
import org.taj.hotel.exceptions.InvalidCityNameException;
import org.taj.hotel.repositry.HotelRepo;

import java.util.List;

@Service
public class HotelService {
  public HotelService(HotelRepo hotelsRepo) {
    this.hotelsRepo = hotelsRepo;
  }

  private final HotelRepo hotelsRepo;

  public List<Hotel> findHotelsByCity(String city) throws InvalidCityNameException {
    if (city.isEmpty()) {
      throw new InvalidCityNameException();
    }

    List<Hotel> hotels = hotelsRepo.findByCity(city);

    if (hotels.isEmpty()) {
      throw new InvalidCityNameException("No hotels found from given city");
    }

    return hotels;
  }
}
