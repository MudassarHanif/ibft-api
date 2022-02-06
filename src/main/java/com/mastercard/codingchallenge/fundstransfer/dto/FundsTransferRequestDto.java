package com.mastercard.codingchallenge.fundstransfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FundsTransferRequestDto {
  @NotNull
  @JsonProperty("sender-account-id")
  private Long senderAccountId;

  @NotNull
  @PositiveOrZero(message = "Transaction amount should not be less than 0")
  @JsonProperty("transaction-amount")
  private BigDecimal transactionAmount;

  @NotNull
  @JsonProperty("receiver-account-id")
  private Long receiverAccountId;

}
