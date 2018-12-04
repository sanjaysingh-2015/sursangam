package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
