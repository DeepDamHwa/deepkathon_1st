package com.example.deepkathon_1.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmojiResponse {
    private Long idx;
    private Integer count;
    private Boolean isMine;
    private List<UserResponse> users;

    private EmojiResponse toDto(){
        return EmojiResponse.builder()
                .idx(this.idx)
                .count(this.count)
                .isMine(this.isMine)
                .users(this.users)
                .build();
    }
}
