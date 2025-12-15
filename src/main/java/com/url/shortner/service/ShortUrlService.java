package com.url.shortner.service;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import com.url.shortner.dto.response.ShortUrlResponse;

public interface ShortUrlService {

    ShortUrlResponse createShortUrl(CreateShortUrlRequest request);

    ShortUrlResponse getOriginalUrl(String shortCode);

    ShortUrlResponse updateShortUrl(String shortCode, CreateShortUrlRequest request);

    void deleteShortUrl(String shortCode);

}
