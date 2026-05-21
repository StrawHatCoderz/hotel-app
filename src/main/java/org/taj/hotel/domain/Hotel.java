package org.taj.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Hotel {
  @Id
  private String id;

  private final String hotelId;

  private final String name;
  private final String city;
  private final int totalRooms;
  private int availableRooms;

  public Hotel(String hotelId, String name, String city, int totalRooms) {
    this.hotelId = hotelId;
    this.name = name;
    this.city = city;
    this.totalRooms = totalRooms;
    this.availableRooms = totalRooms;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hotel hotel = (Hotel) o;
    return totalRooms == hotel.totalRooms && availableRooms == hotel.availableRooms && Objects.equals(hotelId, hotel.hotelId) && Objects.equals(name, hotel.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotelId, name, totalRooms, availableRooms);
  }

  public void setAvailableRooms(int noOfRooms) {
    this.availableRooms = this.availableRooms - noOfRooms;
  }

  @Override
  public String toString() {
    return "Hotel{" +
            "id='" + hotelId + '\'' +
            ", name='" + name + '\'' +
            ", totalRooms=" + totalRooms +
            ", availableRooms=" + availableRooms +
            '}';
  }

  public boolean canBook(int noOfRooms) {
    return availableRooms >= noOfRooms;
  }

  public String getName() {
    return name;
  }
}
