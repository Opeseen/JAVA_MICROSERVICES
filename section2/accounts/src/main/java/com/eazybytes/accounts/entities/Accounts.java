package com.eazybytes.accounts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accounts extends BaseEntity {
  private Long customerId;
  @Id
  private long accountNumber;
  private String accountType;
  private String branchAddress;
}
