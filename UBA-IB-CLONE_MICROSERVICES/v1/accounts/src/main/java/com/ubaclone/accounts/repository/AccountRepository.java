package com.ubaclone.accounts.repository;

import com.ubaclone.accounts.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
  // Fetch all customer accounts
  List<Account> findByCustomerId(Long customerId);
}
