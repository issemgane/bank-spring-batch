package com.appstude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appstude.model.BankTransaction;


public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

}
