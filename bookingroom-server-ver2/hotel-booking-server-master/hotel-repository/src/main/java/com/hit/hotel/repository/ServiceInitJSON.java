package com.hit.hotel.repository;

import com.hit.hotel.repository.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ServiceInitJSON {

    private Long id;

    private String title;

    private String thumbnail;

    private Long price;

    private String description;

    private Set<Product> products = new HashSet<>();

}
