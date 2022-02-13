package com.fundstransfer.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.fundstransfer.domain.model.FundsTransferModel;
import com.fundstransfer.dto.FundsTransferRequestDto;
import com.fundstransfer.dto.LedgerEntryDto;
import com.fundstransfer.service.AccountService;
import com.fundstransfer.transfomer.Transformer;
import com.fundstransfer.validator.RequestDtoValidator;
import com.fundstransfer.dto.AccountBalanceDto;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated // class level
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;
    private final Transformer transformer;

    @RequestMapping(value = "{accountId}/balance", method = GET, produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountBalanceDto> getAccountBalance(@PathVariable @NotNull Long accountId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(accountService.getAccountBalance(accountId));
    }

    @RequestMapping(value = "transferfunds", method = POST, produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity transferFunds(@RequestBody @Validated FundsTransferRequestDto fundsTransferRequestDto)  {
        //Validate iputs
        RequestDtoValidator.validate(fundsTransferRequestDto);
        //transform to domain object before going to service
        FundsTransferModel fundsTransferModel = transformer.transform(fundsTransferRequestDto, FundsTransferModel.class);
        accountService.transferFunds(fundsTransferModel);
        return (ResponseEntity.accepted().build());
    }

    @RequestMapping(value = "{accountNumber}/ministatement", method = GET, headers = "Accept=application/json")
    public ResponseEntity<? extends List> getMiniStatement(@PathVariable @Validated Long accountNumber) {
        return ResponseEntity.ok(accountService.getMiniStatement(accountNumber).stream()
            .map(ledgerModel -> transformer.transform(ledgerModel, LedgerEntryDto.class)).collect(Collectors.toList()));
    }
}
