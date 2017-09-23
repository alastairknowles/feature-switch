package com.tide.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReleaseDto {

	private String version;

	private LocalDateTime createdTimestamp;

	private FeaturesDto features;

	@JsonCreator
	public ReleaseDto(@JsonProperty("version") String version,
	                  @JsonProperty("createdTimestamp") LocalDateTime createdTimestamp) {
		this.version = version;
		this.createdTimestamp = createdTimestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public FeaturesDto getFeatures() {
		return features;
	}

	public void setFeatures(FeaturesDto features) {
		this.features = features;
	}

	public static class FeaturesDto {

		private List<String> inclusions = new ArrayList<>();

		private List<String> exclusions = new ArrayList<>();

		public List<String> getInclusions() {
			return inclusions;
		}

		public void setInclusions(List<String> inclusions) {
			this.inclusions = inclusions;
		}

		public List<String> getExclusions() {
			return exclusions;
		}

		public void setExclusions(List<String> exclusions) {
			this.exclusions = exclusions;
		}

	}

}
