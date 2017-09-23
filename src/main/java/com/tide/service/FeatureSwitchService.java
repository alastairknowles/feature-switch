package com.tide.service;

import com.tide.domain.FeatureSwitch;
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

	public FeatureSwitchService(FeatureSwitchRepository featureSwitchRepository) {
		this.featureSwitchRepository = featureSwitchRepository;
	}

	public ReleaseDto getReleaseMetadata(long releaseId, Long companyId, Long groupId, Long userId) {
		List<FeatureSwitch> featureSwitches =
				featureSwitchRepository.getFeatureSwitches(releaseId, companyId, groupId, userId);
		Map<String, FeatureSwitch> featureSwitchesIndex = new HashMap<>();

		// Filter out duplicates
		for (FeatureSwitch featureSwitch : featureSwitches) {
			String feature = featureSwitch.getFeature();
			if (!featureSwitchesIndex.containsKey(feature)) {
				featureSwitchesIndex.put(feature, featureSwitch);
			}
		}

		// Build the response
		ReleaseDto releaseDto = new ReleaseDto();
		for (FeatureSwitch featureSwitch : featureSwitchesIndex.values()) {
			if (releaseDto.getVersion() == null) {
				releaseDto.setVersion(featureSwitch.getRelease().getVersion());
			}

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
