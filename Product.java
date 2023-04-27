package org.example.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "products")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    long id;

    String name;

    int remain;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    Set<Food> foods = new HashSet<>();

    @Override
    public String toString() {
        return String.format("Product{id=%d, name=%s, remain=%d}", id, name, remain);
    }
}
