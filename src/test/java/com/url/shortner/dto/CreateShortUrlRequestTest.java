package com.url.shortner.dto;

import com.url.shortner.dto.request.CreateShortUrlRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class CreateShortUrlRequestTest {
    private Validator validator;

    @BeforeEach
    void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void shouldPassValidationForValidUrl(){
        CreateShortUrlRequest request =
                new CreateShortUrlRequest();
        request.setUrl("https://www.google.com");
        Set<ConstraintViolation<CreateShortUrlRequest>> violations =
                validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenUrlIsBlank(){
        CreateShortUrlRequest request =
                new CreateShortUrlRequest();
        request.setUrl("");

        Set<ConstraintViolation<CreateShortUrlRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());

        ConstraintViolation<CreateShortUrlRequest> violation =
                violations.iterator().next();

        assertEquals("URL must not be blank",violation.getMessage());
    }

    @Test
    void shouldFailForInvalidUrlFormat(){
        CreateShortUrlRequest request =
                new CreateShortUrlRequest();
        request.setUrl("invalid-url");

        Set<ConstraintViolation<CreateShortUrlRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());

        ConstraintViolation<CreateShortUrlRequest> violation =
                violations.iterator().next();

        assertEquals("Invalid URL format",violation.getMessage());
    }

}
