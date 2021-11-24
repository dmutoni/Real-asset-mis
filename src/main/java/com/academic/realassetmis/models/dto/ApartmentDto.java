package com.academic.realassetmis.models.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApartmentDto {

    @NotNull
    private String name;

    @NotNull
    private String location;

    @NotNull
    private String owner;

    @NotNull
    private int price;

    public ApartmentDto(String name, String location, String owner, int price) {
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.price = price;
    }

}
