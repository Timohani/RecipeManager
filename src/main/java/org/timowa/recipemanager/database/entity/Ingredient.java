package org.timowa.recipemanager.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 128, nullable = false)
    private String name;

    // Count of unit, like 100 (grams)
    @Column(nullable = false)
    private Integer count;

    // Unit of measurement, like gram or liter
    @Column(length = 128, nullable = false)
    private String unit;
}
