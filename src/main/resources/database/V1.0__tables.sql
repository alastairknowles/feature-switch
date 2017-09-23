CREATE TABLE release (
	id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	version           VARCHAR(255)    NOT NULL,
	created_timestamp DATETIME        NOT NULL,
	updated_timestamp DATETIME,
	PRIMARY KEY (id),
	UNIQUE INDEX (version)
)
	COLLATE = utf8_general_ci
	ENGINE = innodb;

CREATE TABLE feature (
	id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	release_id        BIGINT          NOT NULL,
	feature           VARCHAR(255)    NOT NULL,
	company_id        BIGINT UNSIGNED,
	group_id          BIGINT UNSIGNED,
	user_id           BIGINT UNSIGNED,
	state             VARCHAR(10)     NOT NULL,
	created_timestamp DATETIME        NOT NULL,
	updated_timestamp DATETIME,
	PRIMARY KEY (id),
	UNIQUE INDEX (release_id, feature, company_id, group_id, user_id),
	FOREIGN KEY (release_id) REFERENCES release (id)
)
	COLLATE = utf8_general_ci
	ENGINE = innodb;