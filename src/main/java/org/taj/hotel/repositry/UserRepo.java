package org.taj.hotel.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.taj.hotel.domain.User;

public interface UserRepo extends MongoRepository<User, String> {
  User findByUsername(String username);

  User findByUserId(String userId);
}
