package com.group.mission.domain;

import java.time.LocalDate;

public class Fruit {

    private Long id;
    private String name;
    private LocalDate warehousingDate;
    private Long price;
    private FruitStatus status;

    public Fruit(String name, LocalDate warehousingDate, Long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
        this.status = FruitStatus.REGISTERED;
    }

    public Fruit(Long id, String name, LocalDate warehousingDate, Long price, FruitStatus status) {
        this.id = id;
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
        this.status = status;
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
