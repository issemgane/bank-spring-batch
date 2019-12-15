package com.appstude.batch;

import com.appstude.model.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

//@Component
public class BankTransactionAnalyticsProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    private double totalDebit = 0;
    private double totalCredit = 0;
    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        if("D".equals(bankTransaction.getTransactionType())){
            totalDebit+= bankTransaction.getTransactionAmount();
        }

        if("C".equals(bankTransaction.getTransactionType())){
            totalCredit+= bankTransaction.getTransactionAmount();
        }

     //   bankTransaction.setTransactionDate();
        return bankTransaction;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }
}
