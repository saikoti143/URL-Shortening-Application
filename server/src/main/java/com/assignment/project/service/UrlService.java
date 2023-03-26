package com.assignment.project.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.project.exceptions.InvalidURLEnteredException;
import com.assignment.project.model.Url;
import com.assignment.project.repository.UrlRepository;

@Service
public class UrlService implements UrlServices {

  private static final int SHORT_URL_LENGTH = 7;
  private static final int EXPIRATION_TIME_MINUTES = 5;

  @Autowired
  UrlRepository urlRepo;

  public Url generateShortUrl(String originalUrl) throws InvalidURLEnteredException, IOException {
    if (!isValidUrl(originalUrl)) {
      throw new InvalidURLEnteredException("Entered URL is invalid");
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

  private boolean isValidUrl(String url) throws IOException {
	
	Url ur = new Url();
	ur.setOriginalUrl(url);
	
	  try {
	        URL u = new URL(ur.getOriginalUrl());
	        u.toURI(); 
	        try {
	        	HttpURLConnection conn = (HttpURLConnection) u.openConnection();
	        	conn.setRequestMethod("HEAD");
	            int responseCode = conn.getResponseCode();
	            System.out.println(responseCode);
	            return responseCode <500;
	        }
	        catch(Exception e){
	        	return false;
	        }
	        
          
	        	    } catch (MalformedURLException | URISyntaxException e) {
	    	System.out.println(e.getMessage());
	        return false;
	    }
    
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
