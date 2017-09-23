package com.tide.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "release")
public class Release extends BaseEntity {

	@Column(name = "version", nullable = false, unique = true)
	private String version;

	private Set<Feature> features = new HashSet<>();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<Feature> getFeatures() {
		return features;
	}

}
