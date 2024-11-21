package com.example.deepkathon_1.model.entity;

import com.example.deepkathon_1.model.dto.EmojiResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Emoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @OneToOne(mappedBy = "emoji")
    private Interaction interaction;

    private EmojiResponse toDto(Integer count, Boolean isMine){
        return EmojiResponse.builder()
                .idx(this.idx)
                .count(count)
                .isMine(isMine)
                .build();
    }
}
