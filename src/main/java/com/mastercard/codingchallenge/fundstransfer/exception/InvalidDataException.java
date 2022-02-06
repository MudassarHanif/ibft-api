package com.mastercard.codingchallenge.fundstransfer.exception;

public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String messsage) {
    super(messsage);
  }
}
