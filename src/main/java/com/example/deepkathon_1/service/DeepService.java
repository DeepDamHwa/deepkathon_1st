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

        //최상위 댓글만 조회 (parent Idx = null)
        List<Comment> comments = commentRepository.findAllByPostIdxAndParentIdx(postIdx, null);

        List<CommentResponse> responses = new ArrayList<>();

        for (Comment comment : comments) {
            // 해당 comment의 interaction list
            // interaction_idx  comment_idx  user_idx  emoji_idx
            // 1                1            1         1
            // 2                1            1         2
            // 3                1            2         1
            // 4                1            2         3
            // 5                1            2         5
            // 6                1            3         5


            //===============emoji response list==================

            //comment의 interaction 리스트를 emoji로 groupby 해서 가져오기
            // interaction_idx  comment_idx  emoji_idx
            // 1                1            1
            // 2                1            2
            // 4                1            3
            // 5                1            5
            List<Interaction> interactions = interactionRepository.findAllByCommentGroupByEmojiIdx(comment);

            List<EmojiResponse> emojis = new ArrayList<>();

            for (Interaction inte : interactions) {
                // 1번 comment의 1번 이모지를 가진 interaction 리스트 가져오기
                // interaction_idx  comment_idx  user_idx  emoji_idx
                // 1                1            1         1
                // 3                1            2         1
                List<Interaction> users  = interactionRepository.findByEmojiAndComment(inte.getEmoji(), comment);

                List<UserResponse> userResponses = new ArrayList<>();
                for (Interaction user : users) {
                    userResponses.add(user.getUser().toDto());
                }

                emojis.add(inte.getEmoji().toDto(users.size(), users.contains(userIdx), userResponses));

            }

            //===============replies response list==================

            //해당 댓글이 부모인 댓글 조회
            List<Comment> replies = commentRepository.findAllByPostIdxAndParentIdx(postIdx, comment.getIdx());

            List<ReplyResponse> replyResponses = new ArrayList<>();

            //53번줄 과정 똑같이 반복
            for (Comment reply : replies) {
                List<Interaction> replyInteractions = interactionRepository.findAllByCommentGroupByEmojiIdx(reply);

                List<EmojiResponse> replyEmojis = new ArrayList<>();

                for (Interaction inte : replyInteractions) {
                    List<Interaction> users  = interactionRepository.findByEmojiAndComment(inte.getEmoji(), comment);

                    List<UserResponse> userResponses = new ArrayList<>();
                    for (Interaction user : users) {
                        userResponses.add(user.getUser().toDto());
                    }

                    replyEmojis.add(inte.getEmoji().toDto(users.size(), users.contains(userIdx), userResponses));
                }

                replyResponses.add(reply.toDto(reply.getUser().getIdx()==userIdx, replyEmojis));
            }

            responses.add(comment.toDto(comment.getUser().getIdx()==userIdx, emojis, replyResponses));

        }

        return responses;
    }
}