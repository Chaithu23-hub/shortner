package com.url.shortner.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateShortUrlRequest {

    @NotBlank(message = "URL must not be blank")
    @URL(message = "Invalid URL format")
    private String url;
}
