package com.url.shortner.controller;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import com.url.shortner.dto.response.ShortUrlResponse;
import com.url.shortner.dto.response.ShortUrlStatsResponse;
import com.url.shortner.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request){
        ShortUrlResponse response=shortUrlService.createShortUrl(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortUrlResponse> getOriginalUrl(
            @PathVariable("shortCode")  String shortCode){
        ShortUrlResponse response=shortUrlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortUrlResponse> updateShortUrl(
            @PathVariable("shortCode") String shortCode,
            @Valid @RequestBody CreateShortUrlRequest request){
        ShortUrlResponse response=shortUrlService.updateShortUrl(shortCode,request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(
            @PathVariable String shortCode){
        shortUrlService.deleteShortUrl(shortCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<ShortUrlStatsResponse> getStats(
            @PathVariable String shortCode) {

        ShortUrlStatsResponse response =
                shortUrlService.getStats(shortCode);

        return ResponseEntity.ok(response);
    }
}
