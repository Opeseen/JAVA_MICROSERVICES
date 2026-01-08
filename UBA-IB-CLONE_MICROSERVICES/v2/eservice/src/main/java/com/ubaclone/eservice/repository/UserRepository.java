package com.ubaclone.eservice.repository;

import com.ubaclone.eservice.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
  boolean existsByUsername(String username);
  Optional<Users> findByUsername(String username);
  boolean existsByCustomerId(Long customerId);
}
