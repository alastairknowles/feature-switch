package com.tide.service;

import com.tide.domain.Release;
import com.tide.exception.ReleaseException;
import com.tide.repository.ReleaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReleaseService {

	private ReleaseRepository releaseRepository;

	public ReleaseService(ReleaseRepository releaseRepository) {
		this.releaseRepository = releaseRepository;
	}

	public Release getByVersion(String version) {
		Release release = releaseRepository.findByVersion(version);
		if (release == null) {
			throw new ReleaseException("Release: " + version + " does not exist");
		}

		return release;
	}

}
