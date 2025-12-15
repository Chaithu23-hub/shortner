package com.url.shortner.repository;

import com.url.shortner.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository//optional now

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {
    Optional<ShortUrl> findByShortCodeAndDeletedFalse(String shortCode);
    boolean existsByShortCodeAndDeletedFalse(String shortCode);
}
