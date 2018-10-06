package com.stats.service;

import com.stats.Util.SummaryStatisticsToString;
import com.stats.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by Mubashir on 9/8/2018.
 */
@Service
public class StatisticsService {

    @Autowired
    TransactionService transactionService;

    /**
     * TODO: Here we can also create a separate SummaryStatisticsToString repo and SummaryStatistics bean for incremental update however for current scenario it is not useful.
     *
     * @return
     */
    public String getStatistics() {
        return SummaryStatisticsToString.toString(transactionService.getTransactions().parallelStream().collect(Collectors.summarizingDouble(Transaction::getAmount)));
    }

}
