package org.taj.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Booking {
  @Id
  private String id;

  private final String bookingId;
  private final String hotelId;
  private final String userId;
  private final int totalRoomsBooked;

  public Booking(
          String bookingId, String hotelId, String userId,
          int totalRoomsBooked) {
    this.bookingId = bookingId;
    this.hotelId = hotelId;
    this.userId = userId;
    this.totalRoomsBooked = totalRoomsBooked;
  }

  @Id
  public String id() {
    return id;
  }

  public String bookingId() {
    return bookingId;
  }

  public String hotelId() {
    return hotelId;
  }

  public String userId() {
    return userId;
  }

  public int totalRoomsBooked() {
    return totalRoomsBooked;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Booking) obj;
    return Objects.equals(this.id, that.id) &&
            Objects.equals(this.bookingId, that.bookingId) &&
            Objects.equals(this.hotelId, that.hotelId) &&
            Objects.equals(this.userId, that.userId) &&
            this.totalRoomsBooked == that.totalRoomsBooked;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookingId, hotelId, userId, totalRoomsBooked);
  }

  @Override
  public String toString() {
    return "Booking[" +
            "id=" + id + ", " +
            "bookingId=" + bookingId + ", " +
            "hotelId=" + hotelId + ", " +
            "userId=" + userId + ", " +
            "totalRoomsBooked=" + totalRoomsBooked + ']';
  }

}
