package com.tide.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "feature_switch", uniqueConstraints = @UniqueConstraint(columnNames = {"release_id", "feature"}))
public class FeatureSwitch extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "release_id", nullable = false)
	private Release release;

	@Column(name = "feature", nullable = false)
	private String feature;

	// We have to have a priority order for scopes (global -> company -> group -> user)
	// But there will be scenarios where we need to reverse the order - force flag supports that
	@Column(name = "force")
	private Boolean force;

	// Global flag for stuff that is turned on / off for everybody
	// Company / group / user all null expresses the same thing - this just simplifies implementation
	// Should never be used in conjunction with
	@Column(name = "global")
	private Boolean global;

	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "groupId")
	private Long groupId;

	@Column(name = "userId")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private State state;

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	// For any release, it's likely most features will ship, so all we need to do is wrap those at risk
	// Relaxed customers are likely to welcome change, so might want to 'include' experimental features
	// Nervous customers are likely to fear change, so might want to 'exclude' tried and tested features
	public enum State {
		INCLUDE,
		EXCLUDE
	}

}
