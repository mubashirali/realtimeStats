package com.stats.repository;

import com.stats.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static com.stats.Util.Constants.TIME_OUT;
import static java.lang.System.currentTimeMillis;

/**
 * Created by Mubashir on 9/8/2018.
 */
@Repository
public class TransactionRepository {

    //TODO -- Here we can also use Memcached/Ehcache and take advantage of in memory storage + disk storage by using overflowToDisk option.
    BlockingQueue<Transaction> transactions =
            new PriorityBlockingQueue<>(100, Comparator.comparing(Transaction::getTimestamp));

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void updateTransactions() {
        while (!transactions.isEmpty() && !(currentTimeMillis() - transactions.peek().getTimestamp().getTime() <= TIME_OUT)) {
            transactions.poll();
        }
    }

    public BlockingQueue<Transaction> getTransactions() {
        return transactions;
    }

    public void deleteAllTransactions() {
        transactions.clear();
    }
}
