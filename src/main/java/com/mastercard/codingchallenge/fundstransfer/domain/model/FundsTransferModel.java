package com.mastercard.codingchallenge.fundstransfer.domain.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FundsTransferModel {

  /**
   * Corresponds to senderAccountId of FundsTransferRequestDto
   */
  private Long senderAccountNumber;
  /**
   * Corresponds to receiverAccountId of FundsTransferRequestDto
   */
  private Long receiverAccountNumber;
  private BigDecimal transactionAmount;

}
