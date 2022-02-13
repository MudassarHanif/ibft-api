package com.fundstransfer.domain.model;

import com.fundstransfer.domain.constants.CurrencyEnum;
import com.fundstransfer.domain.constants.TransactionTypeEnum;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LedgerModel {
  /**
   * Corresponds to accountId of FundsTransferRequestDto
   */
  private Long accountNumber;

  private CurrencyEnum currencyEnum;
  private TransactionTypeEnum transactionTypeEnum;

  private BigDecimal amount;
  private Date transactionDate;

}
