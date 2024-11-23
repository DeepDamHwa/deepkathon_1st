package com.example.deepkathon_1.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private Long idx;
    private String name;
}
