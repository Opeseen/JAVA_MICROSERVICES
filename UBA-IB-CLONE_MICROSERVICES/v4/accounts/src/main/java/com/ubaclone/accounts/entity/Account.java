package com.ubaclone.accounts.entity;

import com.ubaclone.accounts.enumeration.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "accounts")
public class Account extends BaseEntity{
  @Id
  @Column(name = "account_number")
  private Long accountNumber;

  @Column(name = "balance")
  private Integer balance;

  @Column(name = "account_type")
  @Enumerated(EnumType.STRING)
  private AccountType accountType;

  @Column(name = "customer_id")
  private Long customerId;
}
