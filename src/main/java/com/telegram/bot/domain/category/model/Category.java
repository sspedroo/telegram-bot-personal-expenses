package com.telegram.bot.domain.category.model;

import com.telegram.bot.domain.subcategory.model.Subcategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_category")
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime createAt;
    @OneToMany(mappedBy = "category")
    @Setter(AccessLevel.NONE)
    private List<Subcategory> subcategories;

    @PrePersist
    public void prePersist(){
        createAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        subcategories = new ArrayList<>();
    }

    public void addSubcategory(Subcategory subcategory){
        this.subcategories.add(subcategory);
    }
}
