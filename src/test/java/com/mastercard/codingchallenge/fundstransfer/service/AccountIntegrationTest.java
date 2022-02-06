package com.mastercard.codingchallenge.fundstransfer.service;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-test.properties")
public class AccountIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void givenAccounts_whenGetAccountBalance_thenStatus200()
      throws Exception {

    mvc.perform(get("/accounts/111/balance").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.currency", is("DKK")))
        .andExpect(jsonPath("$.balance", is(50.00)))
        .andExpect(jsonPath("$.account-id", is(111)));

  }

  @Test
  public void givenValidAccountDetails_whenFundsTransfer_thenSuccess()
      throws Exception {

    String fundsTransferRequestJson = "{\"sender-account-id\":\"111\",\"receiver-account-id\":\"222\",\"transaction-amount\":\"10\"}";

    mvc.perform(post("/accounts/transferfunds").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(fundsTransferRequestJson))
        .andExpect(status().isAccepted());

  }

  @Test
  public void givenValidAccountDetails_whenMiniStatement_thenReturnSuccess()
      throws Exception {

    mvc.perform(get("/accounts/111/ministatement").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].currency", is("DKK")))
        .andExpect(jsonPath("$[0].amount", is(100.00)))
        .andExpect(jsonPath("$[0].type", is("CREDIT")))
        .andExpect(jsonPath("$[0].account-id", is(111)));

  }


}
