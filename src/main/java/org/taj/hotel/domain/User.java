package org.taj.hotel.domain;

import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
  @Id
  private String id;

  private final String userId;
  private final String username;
  private final String password;

  public User(String userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public @Nullable String getPassword() {
    return password;
  }
}
