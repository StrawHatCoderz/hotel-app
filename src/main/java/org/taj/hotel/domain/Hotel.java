package org.taj.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Hotel {
  @Id
  private final String id;

  private final String name;
  private final String city;
  private final int totalRooms;
  private int availableRooms;

  public Hotel(String id, String name, String city, int totalRooms) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.totalRooms = totalRooms;
    this.availableRooms = totalRooms;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hotel hotel = (Hotel) o;
    return totalRooms == hotel.totalRooms && availableRooms == hotel.availableRooms && Objects.equals(id, hotel.id) && Objects.equals(name, hotel.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, totalRooms, availableRooms);
  }

  public String getName() {
    return name;
  }

  public int getTotalRooms() {
    return totalRooms;
  }

  public int getAvailableRooms() {
    return availableRooms;
  }

  public void setAvailableRooms(int noOfRooms) {
    this.availableRooms = this.availableRooms - noOfRooms;
  }

  @Override
  public String toString() {
    return "Hotel{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", totalRooms=" + totalRooms +
            ", availableRooms=" + availableRooms +
            '}';
  }

  public boolean canBook(int noOfRooms) {
    return availableRooms >= noOfRooms;
  }
}
