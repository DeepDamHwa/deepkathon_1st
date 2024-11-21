package com.example.deepkathon_1.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@Builder
public class CommentResponse {
    private Long idx;
    private Long writerIdx;
    private String writerRole;
    private String writerName;
    private LocalDateTime createdAt;
    private String content;
    private Boolean isMine;
    private List<EmojiResponse> emojis;
    private List<ReplyResponse> replies;
}
