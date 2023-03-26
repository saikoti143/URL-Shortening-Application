package com.assignment.project.service;

import com.assignment.project.model.Url;

public interface UrlServices {

	public Url generateShortUrl(String originalUrl);
	public Url getOriginalUrl(String shortUrl);
}
