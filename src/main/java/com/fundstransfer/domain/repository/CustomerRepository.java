package com.fundstransfer.domain.repository;

import com.fundstransfer.domain.repository.entity.LedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<LedgerEntity, Integer> {

}