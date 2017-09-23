package com.tide.repository;

import com.tide.domain.FeatureSwitch;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureSwitchRepository extends CrudRepository<FeatureSwitch, Long>,
		JpaSpecificationExecutor<FeatureSwitch> {

	default List<FeatureSwitch> getFeatureSwitches(long releaseId, Long companyId, Long groupId, Long userId) {
		return null;
	}

}
