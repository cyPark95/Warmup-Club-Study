package com.group.mission.domain;

import java.time.LocalDate;

public class Fruit {

    private Long id;
    private String name;
    private LocalDate warehousingDate;
    private Long price;
    private boolean isSold;

    public Fruit(String name, LocalDate warehousingDate, Long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
    }

    public Fruit(Long id, String name, LocalDate warehousingDate, Long price, boolean isSold) {
        this.id = id;
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
        this.isSold = isSold;
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

    public boolean isSold() {
        return isSold;
    }

    public void sold() {
        this.isSold = true;
    }
}
