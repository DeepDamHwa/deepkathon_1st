package com.example.deepkathon_1.model.dto;

import java.util.List;
import lombok.Builder;

@Builder
public class EmojiResponse {
    private Long idx;
    private Integer count;
    private Boolean isMine;
    private List<UserResponse> users;
}
