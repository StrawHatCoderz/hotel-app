package org.taj.hotel.exceptions;

public class InsufficientRoomsException extends Exception {
  public InsufficientRoomsException() {
    super("Insufficient rooms");
  }
}
