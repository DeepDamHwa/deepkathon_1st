package com.example.deepkathon_1.service;

import com.example.deepkathon_1.model.dto.CommentResponse;
import com.example.deepkathon_1.model.dto.EmojiResponse;
import com.example.deepkathon_1.model.dto.ReplyResponse;
import com.example.deepkathon_1.model.dto.UserResponse;
import com.example.deepkathon_1.model.entity.Comment;
import com.example.deepkathon_1.model.entity.Emoji;
import com.example.deepkathon_1.model.entity.Interaction;
import com.example.deepkathon_1.model.entity.User;
import com.example.deepkathon_1.repository.*;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeepService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;
    private final EmojiRepository emojiRepository;
    public List<CommentResponse> getComments(Long userIdx, Long postIdx) {

        //한방 쿼리로 다 조회
        List<Comment> comments = commentRepository.findAllByUserIdxAndPostIdx(userIdx,postIdx);


        List<CommentResponse> responses = new ArrayList<>();

        for (Comment comment : comments) {

            List<Interaction> interactions = interactionRepository.findAllByCommentGroupByEmojiIdx(comment);

            List<EmojiResponse> emojis = new ArrayList<>();

            for (Interaction inte : interactions) {
                List<Interaction> users  = interactionRepository.findByEmojiAndComment(inte.getEmoji(), comment);

                List<UserResponse> userResponses = new ArrayList<>();

                for (Interaction user : users) {
                    userResponses.add(user.getUser().toDto());
                }

                boolean isMine = users.contains(userIdx);

                Integer count = users.size();

                emojis.add(inte.getEmoji().toDto(count, isMine, userResponses));

            }

            responses.add(comment.toDto(true, emojis, null));

        }

        return responses;
    }
}
