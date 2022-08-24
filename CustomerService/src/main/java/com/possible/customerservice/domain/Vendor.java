package com.possible.customerservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vendor {
    private String name;
    private String vendorLogo;
    private String desc;
    private Address vendorAddress;
}
