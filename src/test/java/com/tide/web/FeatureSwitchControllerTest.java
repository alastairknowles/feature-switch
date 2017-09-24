package com.tide.web;

import com.tide.FeatureSwitchApplication;
import com.tide.TestFeatureSwitchApplication;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.nio.charset.StandardCharsets.UTF_8;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
@RunWith(SpringRunner.class)
@Sql("classpath:data/setup.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {FeatureSwitchApplication.class, TestFeatureSwitchApplication.class})
public class FeatureSwitchControllerTest {

	private static final String SERVLET_PATH = "/feature-switch/release-metadata";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void shouldReturn404ForInvalidVersion() throws Exception {
		String actualResponse = mockMvc.perform(
				MockMvcRequestBuilders.get(SERVLET_PATH + "/0.9"))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		Assert.assertTrue(actualResponse.contains("\"explanation\" : \"Release: 0.9 does not exist\""));
	}

	@Test
	public void shouldReturnExpectedFeatureSwitches() throws Exception {
		String actualResponse = mockMvc.perform(
				MockMvcRequestBuilders.get(SERVLET_PATH + "/1.0"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = FileUtils.readFileToString(
				applicationContext.getResource("classpath:response/featureSwitches.json").getFile(), UTF_8).trim();
		Assert.assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void shouldReturnExpectedFeatureSwitchesForUser() throws Exception {
		String response = mockMvc.perform(
				MockMvcRequestBuilders.get(SERVLET_PATH + "/1.0")
						.param("companyId", "1")
						.param("groupId", "1")
						.param("userId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.assertNotNull(response);
	}

}
