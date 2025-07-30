package com.eazybytes.accounts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accounts {
  private Long customerId;
  @Id
  private long accountNumber;
  private String accountType;
  private String branchAddress;
}
