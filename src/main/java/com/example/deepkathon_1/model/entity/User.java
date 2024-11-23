package com.example.deepkathon_1.model.entity;

import com.example.deepkathon_1.model.dto.UserResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Interaction> interactions = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "role_idx")
    private Role role;

    public UserResponse toDto(){
        return UserResponse.builder()
                .idx(this.idx)
                .name(this.name)
                .build();
    }
}
