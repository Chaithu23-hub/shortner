package com.url.shortner.service;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import com.url.shortner.dto.response.ShortUrlResponse;
import com.url.shortner.exception.ResourceNotFoundException;
import com.url.shortner.model.ShortUrl;
import com.url.shortner.repository.ShortUrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ShortUrlServiceImp implements ShortUrlService{

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 7;
    private static final int MAX_RETRIES = 3;

    private final ShortUrlRepository shortUrlRepository;
    private final SecureRandom random=new SecureRandom();

    @Override
    @Transactional
    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {

        int attempts=0;
        ShortUrl savedEntity=null;

        while(attempts<MAX_RETRIES){
            attempts++;

            String shortCode = generateShortCode();

            if(shortUrlRepository.existsByShortCodeAndDeletedFalse(shortCode)){
                continue;
            }
            ShortUrl shortUrl=ShortUrl.builder()
                    .url(request.getUrl())
                    .shortCode(shortCode)
                    .accessCount(0L)
                    .deleted(false)
                    .build();
            try{
                savedEntity = shortUrlRepository.save(shortUrl);
                break;//sucess
            }catch (DataIntegrityViolationException ex){
                // collision occurred at DB level, retry
            }
        }
        if (savedEntity == null) {
            throw new RuntimeException("Failed to generate unique short code after retries");
        }
        return mapToResponse(savedEntity);
    }

    @Override
    @Transactional
    public ShortUrlResponse getOriginalUrl(String shortCode){
        ShortUrl urlEntity=shortUrlRepository.findByShortCodeAndDeletedFalse(shortCode).orElseThrow(
                ()->new ResourceNotFoundException(  "Short URL not found for code: " + shortCode)
        );

        urlEntity.setAccessCount(urlEntity.getAccessCount()+1);
        //shortUrlRepository.save(urlEntity); No need to call save() explicitly due to @Transactional

        return mapToResponse(urlEntity);
    }

    @Override
    @Transactional
    public ShortUrlResponse updateShortUrl(String shortCode, CreateShortUrlRequest request){
        ShortUrl urlEntity=shortUrlRepository.findByShortCodeAndDeletedFalse(shortCode)
                .orElseThrow(()->new ResourceNotFoundException("Short URL not found for code: " + shortCode));
        urlEntity.setUrl(request.getUrl());
        return mapToResponse(urlEntity);
    }

    @Override
    @Transactional
    public void deleteShortUrl(String shortCode) {
        ShortUrl urlEntity=shortUrlRepository.findByShortCodeAndDeletedFalse(shortCode)
                .orElseThrow(()->new ResourceNotFoundException("Short URL not found for code: " + shortCode));
        urlEntity.setDeleted(true);
    }


    private ShortUrlResponse mapToResponse(ShortUrl savedEntity) {
        return new ShortUrlResponse(
                savedEntity.getId(),
                savedEntity.getUrl(),
                savedEntity.getShortCode(),
                savedEntity.getCreatedAt(),
                savedEntity.getUpdatedAt());
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
