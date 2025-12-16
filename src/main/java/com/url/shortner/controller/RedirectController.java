package com.url.shortner.controller;

import com.url.shortner.dto.response.ShortUrlResponse;
import com.url.shortner.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final ShortUrlService shortUrlService;

    @GetMapping("{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        ShortUrlResponse response=shortUrlService.getOriginalUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(response.getUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
