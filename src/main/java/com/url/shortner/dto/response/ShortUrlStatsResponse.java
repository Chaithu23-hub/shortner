package com.url.shortner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShortUrlStatsResponse {
    private Long id;
    private String url;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long accessCount;
}
