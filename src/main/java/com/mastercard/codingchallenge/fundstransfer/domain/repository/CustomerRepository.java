package com.mastercard.codingchallenge.fundstransfer.domain.repository;

import com.mastercard.codingchallenge.fundstransfer.domain.repository.entity.LedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<LedgerEntity, Integer> {

}