package com.appstude.batch;

import com.appstude.model.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BankTransactionProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        bankTransaction.setTransactionDate(formatter.parse(bankTransaction.getStrTransactionDate()));
        return bankTransaction;
    }
}
