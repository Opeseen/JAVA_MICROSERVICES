package com.ubaclone.accounts.repository;

import com.ubaclone.accounts.entity.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<AccountTransaction, Long>{
  Optional<AccountTransaction> findFirstByAccountNumberOrderByTransactionDateDesc(Long accountNumber);
  List<AccountTransaction> findByAccountNumber(Long accountNumber);
}
