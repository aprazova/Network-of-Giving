package com.finaltask.networkofgiving.repository;

import com.finaltask.networkofgiving.model.UsersTransaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<UsersTransaction, Long> {
}
