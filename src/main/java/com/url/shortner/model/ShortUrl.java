package com.url.shortner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name="short_urls",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_short_code",columnNames = "short_code")
        },
    indexes = {
@Index(name = "idx_short_code", columnList = "short_code"),
@Index(name = "idx_short_code_deleted", columnList = "short_code, is_deleted")
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @URL
    @Column(name = "original_url",nullable = false,length = 2048)
    private String url;



    @NotBlank
    @Column(name = "short_code",nullable = false,length = 10,unique = true)
    private String shortCode;

    @NotNull
    @Column(name = "access_count",nullable = false)
    private Long accessCount=0L;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted=false;

}
