package com.ubaclone.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  @Column(name = "customer_id")
  private Long customerId;
}
