package org.taj.hotel.exceptions;

public class InvalidCityNameException extends Exception {
  public InvalidCityNameException() {
    super("Invalid City Name");
  }

  public InvalidCityNameException(String message) {
    super(message);
  }
}
