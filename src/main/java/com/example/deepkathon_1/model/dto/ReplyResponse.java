package com.example.deepkathon_1.model.dto;

import java.time.LocalDateTime;
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
public class ReplyResponse {
    private Long idx;
    private Long writerIdx;
    private String writerRole;
    private String writerName;
    private LocalDateTime createdAt;
    private String content;
    private Boolean isMine;
    private List<EmojiResponse> emojis;
}
