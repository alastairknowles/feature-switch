package com.tide.service;

import com.tide.domain.FeatureSwitch;
import com.tide.domain.Release;
import com.tide.repository.FeatureSwitchRepository;
import com.tide.rest.ReleaseDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FeatureSwitchService {

	private FeatureSwitchRepository featureSwitchRepository;

	private ReleaseService releaseService;

	public FeatureSwitchService(FeatureSwitchRepository featureSwitchRepository, ReleaseService releaseService) {
		this.featureSwitchRepository = featureSwitchRepository;
		this.releaseService = releaseService;
	}

	public ReleaseDto getReleaseMetadata(String version, Long companyId, Long groupId, Long userId) {
		Release release = releaseService.getByVersion(version);
		List<FeatureSwitch> featureSwitches =
				featureSwitchRepository.getFeatureSwitches(release, companyId, groupId, userId);
		Map<String, FeatureSwitch> featureSwitchesIndex = new HashMap<>();

		// Filter out duplicates
		for (FeatureSwitch featureSwitch : featureSwitches) {
			String feature = featureSwitch.getFeature();
			if (!featureSwitchesIndex.containsKey(feature)) {
				featureSwitchesIndex.put(feature, featureSwitch);
			}
		}

		// Build the response
		ReleaseDto releaseDto = new ReleaseDto(release.getVersion(), release.getCreatedTimestamp());
		for (FeatureSwitch featureSwitch : featureSwitchesIndex.values()) {
			ReleaseDto.FeaturesDto featuresDto = releaseDto.getFeatures();
			if (featuresDto == null) {
				featuresDto = new ReleaseDto.FeaturesDto();
				releaseDto.setFeatures(featuresDto);
			}

			if (featureSwitch.getState() == FeatureSwitch.State.INCLUDE) {
				featuresDto.getInclusions().add(featureSwitch.getFeature());
			} else {
				featuresDto.getExclusions().add(featureSwitch.getFeature());
			}
		}

		return releaseDto;
	}

}
