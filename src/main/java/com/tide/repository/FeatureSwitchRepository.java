package com.tide.repository;

import com.tide.domain.FeatureSwitch;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface FeatureSwitchRepository extends CrudRepository<FeatureSwitch, Long>,
		JpaSpecificationExecutor<FeatureSwitch> {

	default List<FeatureSwitch> getFeatureSwitches(long releaseId, Long companyId, Long groupId, Long userId) {
		return findAll((root, query, criteriaBuilder) -> {
			// Match the release
			Predicate predicate = criteriaBuilder.equal(root.get("release.id"), releaseId);

			// Find global switches
			Predicate globalPredicate = criteriaBuilder.isNotNull(root.get("global"));

			// Find switches related to user scope
			Predicate scopedPredicate = null;
			if (companyId != null) {
				scopedPredicate = createOrAppendToScopedPredicate(
						criteriaBuilder, root, null, "companyId", companyId);
			}

			if (groupId != null) {
				scopedPredicate = createOrAppendToScopedPredicate(
						criteriaBuilder, root, scopedPredicate, "groupId", groupId);
			}

			if (userId != null) {
				scopedPredicate = createOrAppendToScopedPredicate(
						criteriaBuilder, root, scopedPredicate, "userId", userId);
			}

			if (scopedPredicate == null) {
				return criteriaBuilder.and(predicate, globalPredicate);
			}

			return criteriaBuilder.and(predicate, criteriaBuilder.or(globalPredicate, scopedPredicate));
			// Scopes envelope one another, order descending by order of precedence, so we get priority first
		}, new Sort(Sort.Direction.DESC, "global", "companyId", "groupId", "userId"));
	}

	default Predicate createOrAppendToScopedPredicate(CriteriaBuilder criteriaBuilder, Root<FeatureSwitch> root,
	                                                  Predicate existingPredicate, String property, Long value) {
		if (existingPredicate == null) {
			existingPredicate = criteriaBuilder.equal(root.get(property), value);
		} else {
			criteriaBuilder.or(existingPredicate, criteriaBuilder.equal(root.get(property), value));
		}

		return existingPredicate;
	}

}
