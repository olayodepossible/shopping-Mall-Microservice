package com.possible.productservice.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {

    private Product product;
    private Integer quantity;
}
