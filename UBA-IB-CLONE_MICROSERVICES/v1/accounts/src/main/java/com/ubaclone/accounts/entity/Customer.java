package com.ubaclone.accounts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "email")
  private String email;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "phone")
  private String phone;

}
