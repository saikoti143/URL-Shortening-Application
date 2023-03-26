package com.assignment.project.model;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalUrl;

  private String shortUrl;

  private LocalDateTime expirationTime;

  public Url() {}

  public Url(String originalUrl, String shortUrl, LocalDateTime expirationTime) {
    this.originalUrl = originalUrl;
    this.shortUrl = shortUrl;
    this.expirationTime = expirationTime;
  }

  public Long getId() {
    return id;
  }


  public String getOriginalUrl() {
	  ObjectMapper mapper = new ObjectMapper();
	    try {
	        JsonNode node = mapper.readTree(originalUrl);
	        return node.get("originalUrl").asText();
	    } catch (IOException e) {
	        return null;
	    }
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expirationTime);
  }
}
