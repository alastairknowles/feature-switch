package com.tide.service;

import com.tide.domain.FeatureSwitch;
import com.tide.repository.FeatureSwitchRepository;
import com.tide.rest.ReleaseDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeatureSwitchService {

	private FeatureSwitchRepository featureSwitchRepository;

	public FeatureSwitchService(FeatureSwitchRepository featureSwitchRepository) {
		this.featureSwitchRepository = featureSwitchRepository;
	}

	public ReleaseDto getReleaseMetadata(long releaseId, Long companyId, Long groupId, Long userId) {
		List<FeatureSwitch> featureSwitches =
				featureSwitchRepository.getFeatureSwitches(releaseId, companyId, groupId, userId);
		return null;
	}

}
