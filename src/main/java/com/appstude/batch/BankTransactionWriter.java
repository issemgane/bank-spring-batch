package com.appstude.batch;

import java.util.List;

import com.appstude.model.BankTransaction;
import com.appstude.repositories.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class BankTransactionWriter implements ItemWriter<BankTransaction> {

	@Autowired
	BankTransactionRepository bankTransactionRepository;
	
	@Override
	public void write(List<? extends BankTransaction> transactions) throws Exception {
		
		System.out.println("Data Saved for transaction : "+transactions);
		bankTransactionRepository.saveAll(transactions);
		
	}

}
