package com.mendeley.coding.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mendeley.coding.TestContext;
import com.mendeley.coding.service.CheckoutService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TestContext.class)
@WebAppConfiguration
public class CheckoutControllerTest {

	private static String controllerMappingPrefix = "http://localhost:8080/checkout";
	
	private MockMvc mockMvc;

	@Mock
	private WebApplicationContext webApplicationContext;

	@Mock
	private CheckoutService checkoutService;

	@InjectMocks
	private CheckoutController sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doReturn(null).when(checkoutService).checkout((any()));
		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
	}

	@Test
	public void checkout_hasOkResponse() throws Exception {
		mockMvc.perform(post(controllerMappingPrefix)).andExpect(status().isOk());		
	}
}
