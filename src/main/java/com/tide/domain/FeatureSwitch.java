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

	// Features can be included / excluded globally by making records with no company / group / user
	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "groupId")
	private Long groupId;

	@Column(name = "userId")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private State state;

	// For any release, it's likely most features will ship, so all we need to do is wrap those at risk
	// Relaxed customers are likely to welcome change, so might want to 'include' experimental features
	// Nervous customers are likely to fear change, so might want to 'exclude' tried and tested features
	public enum State {
		INCLUDE,
		EXCLUDE
	}

}
