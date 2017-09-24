package com.tide.web;

import com.tide.FeatureSwitchApplication;
import com.tide.TestFeatureSwitchApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
@RunWith(SpringRunner.class)
@Sql("classpath:sql/setup.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {FeatureSwitchApplication.class, TestFeatureSwitchApplication.class})
public class FeatureSwitchControllerTest {

	private MockMvc mockMvc;

	public FeatureSwitchControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	public void shouldReturn404ForInvalidVersion() {
		
	}

	@Test
	public void shouldReturnExpectedFeatureSwitches() {

	}

}
