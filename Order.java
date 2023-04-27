package org.example.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "waiter_id", referencedColumnName = "id")
    Waiter waiter;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    Table table;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<Food> foods;

    @Override
    public String toString() {
        return String.format("Order{id=%d, waiter=%s, tableService=%s, foodCount=%d}",
                id, waiter, table.getService(), foods.size());
    }
}
