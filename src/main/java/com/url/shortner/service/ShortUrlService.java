package com.url.shortner.service;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import com.url.shortner.dto.response.ShortUrlResponse;
import com.url.shortner.dto.response.ShortUrlStatsResponse;

public interface ShortUrlService {

    ShortUrlResponse createShortUrl(CreateShortUrlRequest request);

    ShortUrlResponse getOriginalUrl(String shortCode);

    ShortUrlResponse updateShortUrl(String shortCode, CreateShortUrlRequest request);

    void deleteShortUrl(String shortCode);

    ShortUrlStatsResponse getStats(String shortCode);

}
