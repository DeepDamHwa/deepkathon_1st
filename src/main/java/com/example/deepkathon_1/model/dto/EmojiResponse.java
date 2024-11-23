package com.example.deepkathon_1.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmojiResponse {
    private Long idx;
    private Integer count;
    private Boolean isMine;
    private List<UserResponse> users;
}
