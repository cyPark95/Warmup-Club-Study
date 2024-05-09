package com.group.mission.domain.fruit;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private LocalDate warehousingDate;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    private FruitStatus status;

    protected Fruit() {
    }

    public Fruit(String name, LocalDate warehousingDate, Long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
        this.status = FruitStatus.REGISTERED;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }

    public FruitStatus getStatus() {
        return status;
    }

    public void sold() {
        this.status = FruitStatus.SOLD;
    }
}
