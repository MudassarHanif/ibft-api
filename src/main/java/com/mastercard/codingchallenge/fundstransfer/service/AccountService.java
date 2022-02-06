package com.mastercard.codingchallenge.fundstransfer.service;

import com.mastercard.codingchallenge.fundstransfer.dto.AccountBalanceDto;
import com.mastercard.codingchallenge.fundstransfer.domain.constants.TransactionTypeEnum;
import com.mastercard.codingchallenge.fundstransfer.domain.model.FundsTransferModel;
import com.mastercard.codingchallenge.fundstransfer.domain.model.LedgerModel;
import com.mastercard.codingchallenge.fundstransfer.domain.repository.AccountRepository;
import com.mastercard.codingchallenge.fundstransfer.domain.repository.LedgerRepository;
import com.mastercard.codingchallenge.fundstransfer.domain.repository.entity.AccountEntity;
import com.mastercard.codingchallenge.fundstransfer.domain.repository.entity.LedgerEntity;
import com.mastercard.codingchallenge.fundstransfer.exception.AccountNotFoundException;
import com.mastercard.codingchallenge.fundstransfer.exception.InsufficientAccountBalanceException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;

  public AccountBalanceDto getAccountBalance(Long accountNumber) {
    AccountEntity accountEntity = accountRepository.findAccountEntityByAccountNumber(accountNumber)
        .orElseThrow(() -> new AccountNotFoundException("Invalid account number"));

    return AccountBalanceDto.builder()
            .accountId(accountEntity.getAccountNumber())
            .balance(accountEntity.getAccountBalance())
            .Currency(accountEntity.getCurrency())
            .build();
  }

  @Transactional(readOnly = true)
  AccountEntity getAccountEntityAndLock(Long accountNumber) {
    return accountRepository.findAccountEntityByAccountNumber(accountNumber)
            .orElseThrow(AccountNotFoundException::new);
  }


  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void transferFunds(FundsTransferModel fundsTransferModel) {
    log.info("Request to transfer funds. {}", fundsTransferModel);

    AccountEntity fromAccount = getAccountEntityAndLock(fundsTransferModel.getSenderAccountNumber());
    AccountEntity toAccount = getAccountEntityAndLock(fundsTransferModel.getReceiverAccountNumber());

    if(fromAccount.getAccountBalance().compareTo(fundsTransferModel.getTransactionAmount()) < 0) {
      throw new InsufficientAccountBalanceException("Insufficient account balance for account id:" + fromAccount.getAccountId());
    }

    fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(fundsTransferModel.getTransactionAmount()));
    toAccount.setAccountBalance(toAccount.getAccountBalance().add(fundsTransferModel.getTransactionAmount()));

    LedgerEntity ledgerEntityForDebit = createLedger(fundsTransferModel.getTransactionAmount(), fromAccount, TransactionTypeEnum.DEBIT);
    LedgerEntity ledgerEntityForCredit = createLedger(fundsTransferModel.getTransactionAmount(), toAccount, TransactionTypeEnum.CREDIT);

    ledgerRepository.saveAll(Arrays.asList(ledgerEntityForCredit, ledgerEntityForDebit));
  }

  private LedgerEntity createLedger(BigDecimal transactionAmount, AccountEntity account, TransactionTypeEnum transactionTypeEnum) {
    LedgerEntity ledgerEntity = new LedgerEntity();
    ledgerEntity.setAccountEntity(account);
    ledgerEntity.setTransactionType(transactionTypeEnum);
    ledgerEntity.setAmount(transactionAmount);

    return ledgerEntity;

  }

  public List<LedgerModel> getMiniStatement(Long accountNumber) {
    List<LedgerEntity> ledgerEntityList = ledgerRepository.findLedgerEntitiesByAccountEntity_AccountNumber(accountNumber);

    return ledgerEntityList.stream()
        .map(ledgerEntity -> LedgerModel.builder()
            .accountNumber(ledgerEntity.getAccountEntity().getAccountNumber())
            .currencyEnum(ledgerEntity.getAccountEntity().getCurrency())
            .amount(ledgerEntity.getAmount())
            .transactionTypeEnum(ledgerEntity.getTransactionType())
            .currencyEnum(ledgerEntity.getCurrency())
            .transactionDate(ledgerEntity.getTransactionDate())
            .build())
        .collect(Collectors.toList());
  }
}
