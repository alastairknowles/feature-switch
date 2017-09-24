package com.tide.web;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import com.tide.exception.ReleaseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class FeatureSwitchControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureSwitchControllerAdvice.class);

	@ExceptionHandler(ReleaseException.class)
	public ResponseEntity<Object> handlePermissionException(Exception exception, WebRequest request) {
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

		return new ResponseEntity<>(servletRequest, responseStatus);
	}

}
