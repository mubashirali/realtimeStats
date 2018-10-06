package com.stats.controller;

import com.stats.model.Transaction;
import com.stats.service.StatisticsService;
import com.stats.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

import static com.stats.Util.Constants.TIME_OUT;
import static java.lang.System.currentTimeMillis;

/**
 * Created by Mubashir on 9/8/2018.
 */
@RestController
public class BaseController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    StatisticsService statisticsService;

    @PostMapping(value = "/transactions")
    public void addTransaction(@Valid @RequestBody Transaction transaction, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            //TODO -- Here we can also implement custom exception class as well.
            throw new ValidationException(bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
        } else if ((currentTimeMillis() - transaction.getTimestamp().getTime()) <= TIME_OUT) {
            transactionService.saveTransaction(transaction);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @DeleteMapping(value = "/transactions")
    public void deleteAllTransactions(HttpServletResponse response) {
        transactionService.deleteAll();
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/statistics")
    public String getStatistics(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        return statisticsService.getStatistics();
    }


}
