package com.fundstransfer.domain.repository.entity;

import com.fundstransfer.domain.constants.CurrencyEnum;
import com.fundstransfer.domain.constants.TransactionTypeEnum;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ledger")
@Data
public class LedgerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer")
    private Integer ledgerId;

    @Column
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private AccountEntity accountEntity;

    @Column(nullable = false)
    private Date transactionDate;

    @Column(nullable = false)
    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.transactionDate = new Date();
    }
}
