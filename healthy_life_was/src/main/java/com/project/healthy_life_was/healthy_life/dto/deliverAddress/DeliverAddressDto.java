package com.project.healthy_life_was.healthy_life.dto.deliverAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class DeliverAddressDto {
    private String address;
    private String addressDetail;
    private int postNum;
}
