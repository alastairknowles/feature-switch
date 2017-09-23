package com.tide.web;

import com.tide.rest.ReleaseDto;
import com.tide.service.FeatureSwitchService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feature-switch")
public class FeatureSwitchController {

	private FeatureSwitchService featureSwitchService;

	public FeatureSwitchController(FeatureSwitchService featureSwitchService) {
		this.featureSwitchService = featureSwitchService;
	}

	// Assume we know company and group for user after login
	// Probably users could be assigned to multiple groups - worry about that if it happens
	// Maybe there is a public component to the app for which all values would be null - assume that is supported
	@GetMapping("/release-metadata/{releaseId}")
	public ReleaseDto getReleaseMetadata(@PathVariable("releaseId") Long releaseId,
	                                     @RequestParam(name = "companyId", required = false) Long companyId,
	                                     @RequestParam(value = "groupId", required = false) Long groupId,
	                                     @RequestParam(value = "userId", required = false) Long userId) {
		return featureSwitchService.getReleaseMetadata(releaseId, companyId, groupId, userId);
	}

}
