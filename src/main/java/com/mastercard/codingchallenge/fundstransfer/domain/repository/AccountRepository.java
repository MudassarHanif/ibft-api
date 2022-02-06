package com.mastercard.codingchallenge.fundstransfer.domain.repository;

import com.mastercard.codingchallenge.fundstransfer.domain.repository.entity.AccountEntity;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

  Optional<AccountEntity> findAccountEntityByAccountNumber(Long accountNumber);

  @Transactional
  @Lock(LockModeType.OPTIMISTIC)
  @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = :accountNumber")
  Optional<AccountEntity> findAccountEntityAndLock(Long accountNumber);


}