package org.timowa.recipemanager.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 128, nullable = false)
    private String name;

    private String description;

    // List of cooking steps
    @ElementCollection
    private List<String> steps = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
