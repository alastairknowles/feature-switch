package com.tide.repository;

import com.tide.domain.FeatureSwitch;
import com.tide.domain.Release;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ReleaseRepository extends CrudRepository<Release, Long>  {

	Release findByVersion(String version);

}
