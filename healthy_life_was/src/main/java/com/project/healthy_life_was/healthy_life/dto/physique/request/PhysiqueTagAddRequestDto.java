package com.project.healthy_life_was.healthy_life.dto.physique.request;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PhysiqueTagAddRequestDto {
    private Set<Long> physiqueTagIds = new HashSet<>();
}