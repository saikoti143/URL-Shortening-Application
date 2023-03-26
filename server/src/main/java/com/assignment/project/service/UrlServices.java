package com.assignment.project.service;

import java.io.IOException;

import com.assignment.project.exceptions.InvalidURLEnteredException;
import com.assignment.project.model.Url;

public interface UrlServices {

	public Url generateShortUrl(String originalUrl) throws InvalidURLEnteredException, IOException;
	public Url getOriginalUrl(String shortUrl);
}
