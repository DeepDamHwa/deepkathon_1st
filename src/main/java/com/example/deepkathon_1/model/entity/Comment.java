package com.example.deepkathon_1.model.entity;

import com.example.deepkathon_1.model.dto.CommentResponse;
import com.example.deepkathon_1.model.dto.EmojiResponse;
import com.example.deepkathon_1.model.dto.ReplyResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String content;
    private Boolean isMine;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<Interaction> interactions = new ArrayList<>();

    //자기참조
    @OneToMany(mappedBy = "parent")
    private List<Comment> replies = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_idx")
    private Comment parent;

    private CommentResponse toDto(User user, Boolean isMine, List<EmojiResponse> emojis, List<ReplyResponse> replies){
        return CommentResponse.builder()
                .idx(this.idx)
                .content(this.content)
                .createdAt(this.getCreatedAt())
                .writerIdx(user.getIdx())
                .writerRole(user.getRole().getName())
                .writerName(user.getName())
                .isMine(isMine)
                .emojis(emojis)
                .replies(replies)
                .build();
    }

    private ReplyResponse toDto(User user, Boolean isMine, List<EmojiResponse> emojis){
        return ReplyResponse.builder()
                .idx(this.idx)
                .content(this.content)
                .createdAt(this.getCreatedAt())
                .writerIdx(user.getIdx())
                .writerRole(user.getRole().getName())
                .writerName(user.getName())
                .isMine(isMine)
                .emojis(emojis)
                .build();
    }
}
