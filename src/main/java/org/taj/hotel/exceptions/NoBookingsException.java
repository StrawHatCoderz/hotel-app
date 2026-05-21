package org.taj.hotel.exceptions;

public class NoBookingsException extends Exception {
  public NoBookingsException() {
    super("No Bookings Registered");
  }
}
