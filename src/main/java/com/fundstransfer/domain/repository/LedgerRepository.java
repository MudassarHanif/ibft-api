package com.fundstransfer.domain.repository;

import com.fundstransfer.domain.repository.entity.LedgerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<LedgerEntity, Integer> {

  List<LedgerEntity> findLedgerEntitiesByAccountEntity_AccountNumber(Long accountNumber);
}