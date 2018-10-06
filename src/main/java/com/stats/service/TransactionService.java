package com.stats.service;

import com.stats.model.Transaction;
import com.stats.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

import static com.stats.Util.Constants.CRON_DELAY;

/**
 * Created by Mubashir on 9/8/2018.
 */
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction){
        transactionRepository.saveTransaction(transaction);
    }

    /**
     * TODO -- Here we can also use a data structure having data expiry options like Memcached.
     */
    @Scheduled(fixedDelay = CRON_DELAY)
    public void updateTransactions(){
        transactionRepository.updateTransactions();
    }

    public void deleteAll() {
        transactionRepository.deleteAllTransactions();
    }

    public BlockingQueue<Transaction> getTransactions(){
        return transactionRepository.getTransactions();
    }
}
