package com.assignment.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.project.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

  Url findByOriginalUrl(String originalUrl);

  Url findByShortUrl(String shortUrl);

  boolean existsByoriginalUrl(String originalUrl);
}
	