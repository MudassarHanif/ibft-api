package com.fundstransfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountBalanceDto {
    @JsonProperty("account-id")
    Long accountId;
    BigDecimal balance;
    Enum Currency;
}
