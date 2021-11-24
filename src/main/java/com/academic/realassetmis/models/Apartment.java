package com.academic.realassetmis.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "price", nullable = false)
    private int price;

    public Apartment() {
    }

    public Apartment(UUID id, String name, String location, String owner, int price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.price = price;
    }

    public Apartment(String name, String location, String owner, int price) {
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.price = price;
    }
}