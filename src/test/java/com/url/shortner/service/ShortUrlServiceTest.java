package com.url.shortner.service;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import com.url.shortner.dto.response.ShortUrlResponse;
import com.url.shortner.exception.ResourceNotFoundException;
import com.url.shortner.model.ShortUrl;
import com.url.shortner.repository.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private ShortUrlServiceImp shortUrlServiceImp;

    @Test
    void shouldCreateShortUrlSuccessfully(){
        CreateShortUrlRequest createShortUrlRequest=
                new CreateShortUrlRequest("https://www.google.com");

        when(shortUrlRepository.save(any(ShortUrl.class)))
                .thenAnswer(invocation->{
                    ShortUrl entity=invocation.getArgument(0);
                    entity.setId(1L);
                    return entity;
                });

        ShortUrlResponse response=
                shortUrlServiceImp.createShortUrl(createShortUrlRequest);

        assertNotNull(response);
        assertEquals("https://www.google.com",response.getUrl());
        assertNotNull(response.getShortCode());

        verify(shortUrlRepository,times(1)).save(any());
    }

    @Test
    void shouldReturnOriginalUrlAndIncrementCount(){
        ShortUrl entity=ShortUrl.builder()
                .id(1L)
                .url("https://google.com")
                .shortCode("abcd123")
                .accessCount(5L)
                .deleted(false)
                .build();
        when(shortUrlRepository.findByShortCodeAndDeletedFalse("abcd123"))
                .thenReturn(Optional.of(entity));

        ShortUrlResponse response =
                shortUrlServiceImp.getOriginalUrl("abcd123");

        assertEquals("https://google.com",response.getUrl());
        assertEquals(6L,entity.getAccessCount());

        verify(shortUrlRepository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenShortCodeNotFound(){
        when(shortUrlRepository.findByShortCodeAndDeletedFalse("xyz1234"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->shortUrlServiceImp.getOriginalUrl("xyz1234"));
    }

    @Test
    void shouldUpdateOriginalUrl(){
        ShortUrl entity=ShortUrl.builder()
                .id(1L)
                .url("https://old.com")
                .shortCode("abcd123")
                .accessCount(5L)
                .deleted(false)
                .build();
        when(shortUrlRepository.findByShortCodeAndDeletedFalse("abcd123"))
                .thenReturn(Optional.of(entity));

        CreateShortUrlRequest request=
                new CreateShortUrlRequest("https://new.com");

        ShortUrlResponse response=shortUrlServiceImp.updateShortUrl("abcd123",request);

        assertEquals("https://new.com",response.getUrl());
    }

    @Test
    void shouldSoftDeleteShortUrl(){
        ShortUrl entity=ShortUrl.builder()
                .id(1L)
                .url("https://old.com")
                .shortCode("abcd123")
                .accessCount(5L)
                .deleted(false)
                .build();

        when(shortUrlRepository.findByShortCodeAndDeletedFalse("abcd123"))
                .thenReturn(Optional.of(entity));

        shortUrlServiceImp.deleteShortUrl("abcd123");

        assertTrue(entity.isDeleted());
    }
}
