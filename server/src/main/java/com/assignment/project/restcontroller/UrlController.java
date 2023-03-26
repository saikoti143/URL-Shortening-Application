package com.assignment.project.restcontroller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.project.exceptions.DuplicateUrlException;
import com.assignment.project.exceptions.UrlExpiredException;
import com.assignment.project.exceptions.UrlNotFoundException;
import com.assignment.project.model.Url;
import com.assignment.project.repository.UrlRepository;
import com.assignment.project.service.UrlService;

@RestController
@RequestMapping("/api")

public class UrlController {

	@Autowired
	private UrlService urlService;
	@Autowired
	UrlRepository urlrepo;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/urls")
	public ResponseEntity<Url> generateShortUrl(@RequestBody String originalUrl) throws DuplicateUrlException {

		if (urlrepo.existsByoriginalUrl(originalUrl)) {
			throw new DuplicateUrlException("Duplicate URL,Given URL already exist in DB");
		}

		Url url = urlService.generateShortUrl(originalUrl);
		if (url == null) {
			return ResponseEntity.badRequest().body(url);
		}

		String shortUrl = "http://localhost:8080/" + url.getShortUrl();

		System.out.println(shortUrl);
		return ResponseEntity.ok(url);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{shortUrl}")
	public void redirectToLongUrl(@PathVariable String shortUrl, HttpServletResponse response)
			throws IOException, UrlExpiredException {
		// Find the long URL for the given short URL
		Url url = urlService.getOriginalUrl(shortUrl);

		if (url == null) {
			// If the short URL does not exist, send a 404 Not Found response
			throw new UrlNotFoundException(shortUrl + " is not a shortedned URL try Shortened the URL");
//          response.setStatus(HttpStatus.NOT_FOUND.value());
		}

		else if (url.isExpired())

		{
			// If the short URL has expired
			throw new UrlExpiredException("Given URL has Expired");

		} else {
			// If the short URL is valid, redirect the user to the long URL
//    	  String originalUrl = url.getOriginalUrl();

			response.sendRedirect(url.getOriginalUrl());
		}
	}

}
