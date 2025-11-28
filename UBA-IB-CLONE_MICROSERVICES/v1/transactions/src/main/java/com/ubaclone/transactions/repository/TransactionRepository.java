package com.ubaclone.transactions.repository;

import com.ubaclone.transactions.entity.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<AccountTransaction, Long>{
}
