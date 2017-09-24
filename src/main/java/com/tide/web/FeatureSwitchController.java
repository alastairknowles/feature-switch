package com.tide.web;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import com.tide.exception.ReleaseException;
import com.tide.rest.ReleaseDto;
import com.tide.service.FeatureSwitchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feature-switch")
public class FeatureSwitchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureSwitchController.class);

	private FeatureSwitchService featureSwitchService;

	public FeatureSwitchController(FeatureSwitchService featureSwitchService) {
		this.featureSwitchService = featureSwitchService;
	}

	// Assume we know company and group for user after login
	// Probably users could be assigned to multiple groups - worry about that if it happens
	// Maybe there is a public component to the app for which all values would be null - assume that is supported
	@GetMapping("/release-metadata/{version:.+}")
	public ReleaseDto getReleaseMetadata(@PathVariable("version") String version,
	                                     @RequestParam(name = "companyId", required = false) Long companyId,
	                                     @RequestParam(value = "groupId", required = false) Long groupId,
	                                     @RequestParam(value = "userId", required = false) Long userId) {
		return featureSwitchService.getReleaseMetadata(version, companyId, groupId, userId);
	}

	@ExceptionHandler(ReleaseException.class)
	public ResponseEntity<Object> handleReleaseException(Exception exception, WebRequest request) {
		String explanation = exception.getMessage();
		LOGGER.warn(explanation, exception);

		HttpStatus responseStatus = HttpStatus.NOT_FOUND;
		HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
		Map<String, Object> response = ImmutableMap.<String, Object>builder()
				.put("timestamp", LocalDateTime.now())
				.put("uri", Joiner.on("?").skipNulls().join(
						servletRequest.getRequestURI(), servletRequest.getQueryString()))
				.put("error", HttpStatus.NOT_FOUND.getReasonPhrase())
				.put("explanation", explanation)
				.build();

		return new ResponseEntity<>(response, responseStatus);
	}

}
