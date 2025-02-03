package com.project.healthy_life_was.healthy_life.dto.deliverAddress;

import com.project.healthy_life_was.healthy_life.entity.deliverAddress.DeliverAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeliverAddressDto {
    private String address;
    private String addressDetail;
    private int postNum;

    public DeliverAddressDto(DeliverAddress deliverAddress) {
        this.address = deliverAddress.getAddress();
        this.addressDetail = deliverAddress.getAddressDetail();
        this.postNum = deliverAddress.getPostNum();
    }
}
