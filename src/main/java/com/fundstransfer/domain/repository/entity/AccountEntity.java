package com.fundstransfer.domain.repository.entity;

import com.fundstransfer.domain.constants.CurrencyEnum;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    @Column(nullable = false)
    @Version
    private Long version;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }
}
