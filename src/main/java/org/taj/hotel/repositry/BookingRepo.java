package org.taj.hotel.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.taj.hotel.domain.Booking;
import org.taj.hotel.domain.Hotel;

import java.util.List;

@Repository
public interface BookingRepo extends MongoRepository<Booking, String> {
  List<Booking> findByUserId(String userId);
}
