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

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "bvn")
  private Long bvn;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "branch_address")
  private String branchAddress;
}
