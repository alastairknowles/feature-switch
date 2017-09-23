package com.tide.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "createdTimestamp", "updatedTimestamp"})
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_timestamp", nullable = false)
	private LocalDateTime createdTimestamp;

	@Column(name = "updated_timestamp")
	private LocalDateTime updatedTimestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public LocalDateTime getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

}
