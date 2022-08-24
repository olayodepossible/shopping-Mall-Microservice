package com.possible.shoppingservicequery.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vendor {
    private String name;
    private String desc;
    private Address vendorAddress;
}
