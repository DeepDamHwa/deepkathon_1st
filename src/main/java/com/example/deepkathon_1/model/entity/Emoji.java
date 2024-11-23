package com.example.deepkathon_1.model.entity;

import com.example.deepkathon_1.model.dto.EmojiResponse;
import com.example.deepkathon_1.model.dto.UserResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Emoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

//    @OneToOne(mappedBy = "emoji")
//    private Interaction interaction;

    public EmojiResponse toDto(Integer count, Boolean isMine, List<UserResponse> users){
        return EmojiResponse.builder()
                .idx(this.idx)
                .count(count)
                .users(users)
                .isMine(isMine)
                .build();
    }
}
