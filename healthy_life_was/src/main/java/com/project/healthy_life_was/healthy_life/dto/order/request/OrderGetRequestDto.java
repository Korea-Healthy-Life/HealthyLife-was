package com.project.healthy_life_was.healthy_life.dto.order.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetRequestDto {
    private Date startOrderDate;
    private Date endOrderDate;
}
