package com.tide.web;

import com.tide.FeatureSwitchApplication;
import com.tide.TestFeatureSwitchApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
@RunWith(SpringRunner.class)
@Sql("classpath:sql/setup.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {FeatureSwitchApplication.class, TestFeatureSwitchApplication.class})
public class FeatureSwitchControllerTest {

	private static final String SERVLET_PATH = "/feature-switch/release-metadata";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturn404ForInvalidVersion() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(SERVLET_PATH + "/0.9"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void shouldReturnExpectedFeatureSwitches() {

	}

}
