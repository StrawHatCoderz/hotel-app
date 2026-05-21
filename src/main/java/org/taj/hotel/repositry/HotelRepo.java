package org.taj.hotel.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.taj.hotel.domain.Hotel;

import java.util.List;

@Repository
public interface HotelRepo extends MongoRepository<Hotel, String> {
  List<Hotel> findByCity(String city);

  Hotel findHotelByHotelId(String hotelId);
}
