package com.assignment.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.project.model.Url;
import com.assignment.project.repository.UrlRepository;

@Service
public class UrlService implements UrlServices {

  private static final int SHORT_URL_LENGTH = 7;
  private static final int EXPIRATION_TIME_MINUTES = 5;

  @Autowired
  UrlRepository urlRepo;

  public Url generateShortUrl(String originalUrl) {
    if (!isValidUrl(originalUrl)) {
      return null;
    }
    String shortUrl = generateRandomString(SHORT_URL_LENGTH);
    Url url = new Url(originalUrl, shortUrl, LocalDateTime.now().plusMinutes(EXPIRATION_TIME_MINUTES));
    urlRepo.save(url);

    return url;
  }

  public Url getOriginalUrl(String shortUrl) {
	  Url url = urlRepo.findByShortUrl(shortUrl);

    if (url != null) {
      return url;
    }
    return null;
  }

  private boolean isValidUrl(String url) {
    // Implement your URL validation logic here
    return true;
  }

  private String generateRandomString(int length) {
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int index = (int) (Math.random() * chars.length());
      sb.append(chars.charAt(index));
    }
    return sb.toString();
  }
}
