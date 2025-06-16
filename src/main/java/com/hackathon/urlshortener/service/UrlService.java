package com.hackathon.urlshortener.service;

import com.hackathon.urlshortener.entity.Url;
import com.hackathon.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private SecureRandom random = new SecureRandom();

    public String shortenUrl(String originalUrl) {
        String shortUrl = generateShortUrl();

        Url existingUrl = urlRepository.findByShortUrl(shortUrl);
        if (existingUrl != null) {
            return existingUrl.getShortUrl();
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        return url != null ? url.getOriginalUrl() : null;
    }

    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARSET.length());
            shortUrl.append(CHARSET.charAt(index));
        }
        return shortUrl.toString();
    }
}