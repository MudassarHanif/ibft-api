package com.fundstransfer.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InsufficientAccountBalanceException extends RuntimeException {

  public InsufficientAccountBalanceException(String messsage) {
    super(messsage);
  }

}
