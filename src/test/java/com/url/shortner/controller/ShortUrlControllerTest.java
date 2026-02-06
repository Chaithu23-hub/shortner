//package com.url.shortner.controller;
//
//import com.url.shortner.dto.response.ShortUrlResponse;
//import com.url.shortner.service.ShortUrlService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//import java.time.LocalDateTime;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ShortUrlController.class)
//public class ShortUrlControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ShortUrlService shortUrlService;
//
//    @Test
//    void shouldCreateShortUrl() throws Exception {
//        ShortUrlResponse response=new ShortUrlResponse(
//                1L,
//                "https://google.com",
//                "abc123",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//
//        when(shortUrlService.createShortUrl(any()))
//                .thenReturn(response);
//
//        mockMvc.perform(post("/shorten")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                          "url": "https://google.com"
//                        }
//                        """))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.shortCode").value("abc123"))
//                .andExpect(jsonPath("$.url").value("https://google.com"));
//
//    }
//
//    @Test
//    void shouldFailWhenUrlIsBlank() throws Exception {
//
//        mockMvc.perform(post("/shorten")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                {
//                  "url": ""
//                }
//            """))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldGetOriginalUrlSuccessfully() throws Exception {
//        ShortUrlResponse response = new ShortUrlResponse(
//                1L,
//                "https://google.com",
//                "abc123",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//
//        when(shortUrlService.getOriginalUrl("abc123"))
//                .thenReturn(response);
//
//        mockMvc.perform(get("/shorten/{shortCode}", "abc123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.url").value("https://google.com"))
//                .andExpect(jsonPath("$.shortCode").value("abc123"));
//
//    }
//    @Test
//    void shouldUpdateShortUrlSuccessfully() throws Exception {
//        ShortUrlResponse response = new ShortUrlResponse(
//                1L,
//                "https://github.com",
//                "abc123",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//
//        when(shortUrlService.updateShortUrl(any(), any()))
//                .thenReturn(response);
//
//        mockMvc.perform(put("/shorten/{shortCode}", "abc123")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                    {
//                      "url": "https://github.com"
//                    }
//                """))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.url").value("https://github.com"))
//                .andExpect(jsonPath("$.shortCode").value("abc123"));
//    }
//    @Test
//    void shouldDeleteShortUrlSuccessfully() throws Exception {
//
//        mockMvc.perform(delete("/shorten/{shortCode}", "abc123"))
//                .andExpect(status().isNoContent());
//    }
//
//
//}
