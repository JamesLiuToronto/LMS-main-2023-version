package com.cognifia.lms.account.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.service.AccountService;

@WebMvcTest
public class AccountControllerTest {
    private final AccountService service = mock(AccountService.class);
    @InjectMocks
    private AccountController controller;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @BeforeMethod
    public void setup() {
        reset(service);
    }

    @Test
    public void getAccountById() throws Exception {
        Account account = new Account();
        account.setUserId(123);
        when(service.getAccountById(eq(123))).thenReturn(account);

        MvcResult result = mockMvc.perform(get("/api/account/{userId}", 123).contentType("application/json"))
                                  .andExpect(status().isOk()).andReturn();
        Account actual = mapper.readValue(result.getResponse().getContentAsString(), Account.class);
        assertEquals(actual.getUserId(), 123);
    }
}
