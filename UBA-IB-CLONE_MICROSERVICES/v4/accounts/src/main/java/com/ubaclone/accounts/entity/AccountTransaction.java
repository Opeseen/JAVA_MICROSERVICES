package com.ubaclone.accounts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "account_transactions")
public class AccountTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id" )
  private Long transactionId;

  @Column(name = "account_number" )
  private Long accountNumber;

  @Column(name = "customer_id" )
  private Long customerId;

  @Column(name = "transaction_dt" )
  private LocalDateTime transactionDate;

  @Column(name = "transaction_summary" )
  private String transactionSummary;

  @Column(name = "transaction_type" )
  private String transactionType;

  @Column(name = "transaction_amt" )
  private Integer transactionAmount;

  @Column(name = "closing_balance" )
  private Integer closingBalance;

  @Column(name = "created_at", updatable = false )
  @CreationTimestamp
  private LocalDateTime createdAt;
}

