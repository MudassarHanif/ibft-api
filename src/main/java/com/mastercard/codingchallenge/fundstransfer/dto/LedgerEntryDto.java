package com.mastercard.codingchallenge.fundstransfer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LedgerEntryDto {
    @JsonProperty("account-id")
    private Long accountNumber;
    @JsonProperty("amount")
    BigDecimal amount;
    @JsonProperty("currency")
    private Enum currencyEnum;
    @JsonProperty("type")
    private Enum transactionTypeEnum;
    @JsonProperty("transaction-date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:MM")
    private Date transactionDate;
}
