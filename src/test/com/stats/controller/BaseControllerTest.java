package com.stats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stats.Application;
import com.stats.Util.Constants;
import com.stats.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Mubashir on 9/9/2018.
 */


@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
/**
 * //TODO these are just a sample Unit tests too many scenarios can be covered (most of them already covered in automated unit tests) i.e invalid input, past and future timestamp, transactions older then 60 seconds etc
 */
public class BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("11230.4"));
        transaction.setTimestamp(new Date());

        ResultActions resultActions = mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transaction))
                .contentType(MediaType.APPLICATION_JSON_UTF8));
        MockHttpServletResponse mockResponse = resultActions.andReturn()
                .getResponse();

        Assert.assertEquals(mockResponse.getStatus(),HttpServletResponse.SC_CREATED);

    }

    @Test
    public void deleteAllTransactions() throws Exception {
        addTransaction();
        ResultActions resultActions = mockMvc.perform(delete("/transactions"));
        MockHttpServletResponse mockResponse = resultActions.andReturn()
                .getResponse();

        Assert.assertEquals(mockResponse.getStatus(), HttpServletResponse.SC_NO_CONTENT);
        Assert.assertTrue(mockResponse.getContentLength() == 0);
    }

    @Test
    public void getStatistics() throws Exception {
        Thread.sleep(Constants.TIME_OUT);
        String response = "{\"sum\": \"22460.80\", \"avg\": \"11230.40\", \"max\": \"11230.40\", \"min\": \"11230.40\", \"count\": 2}";
        addTransaction();
        addTransaction();
        ResultActions resultActions = mockMvc.perform(get("/statistics"));
        MockHttpServletResponse mockResponse = resultActions.andReturn()
                .getResponse();

        Assert.assertTrue(mockResponse.getContentLength() > 0);
        Assert.assertTrue(mockResponse.getContentAsString().equals(response));

    }

}