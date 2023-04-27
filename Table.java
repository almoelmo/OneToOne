package org.example.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    int seats;

    String service;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "table", fetch = FetchType.LAZY)
    Order order;

    @Override
    public String toString() {
        return String.format("Table{id=%d, seats=%s, service=%s, orderId=%d}", id, seats, service, order.getId());
    }
}
