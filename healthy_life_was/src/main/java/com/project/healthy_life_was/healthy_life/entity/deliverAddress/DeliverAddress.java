package com.project.healthy_life_was.healthy_life.entity.deliverAddress;

import com.project.healthy_life_was.healthy_life.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliverAddress {
    @Id
    @Column (name = "deliver_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliverAddressId;

    @OneToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column (name = "address")
    private String address;

    @Column (name = "address_detail")
    private String addressDetail;

    @Column (name = "post_num")
    private int postNum;
}