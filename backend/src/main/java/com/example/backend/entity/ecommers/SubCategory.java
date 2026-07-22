package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id",referencedColumnName = "id")
    private Category category;

    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public record subCategoryDisplay(Long id,Long categoryId,String name,String description){}
}
