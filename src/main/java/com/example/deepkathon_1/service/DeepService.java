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
import java.util.HashMap;
import java.util.Map;
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

    // 댓글 목록을 조회
//    List<Comment> comments = commentRepository.findAllByUserIdxAndPostIdx(userIdx, postIdx);
    List<Comment> comments = commentRepository.findAllByPostIdx(postIdx);

    // 각 댓글에 대해 필요한 데이터를 조합하여 CommentResponse를 생성
    List<CommentResponse> commentResponses = new ArrayList<>();
    for (Comment comment : comments) {
        User writer = comment.getUser();
        Boolean isMine = writer.getIdx().equals(userIdx);

        // 댓글에 대한 이모지 정보
        List<EmojiResponse> emojiResponses = getEmojiResponses(comment,userIdx);

        // 댓글에 대한 답글 정보
        List<ReplyResponse> replyResponses = getReplyResponses(comment, userIdx);

        // CommentResponse 생성
        CommentResponse commentResponse = CommentResponse.builder()
                .idx(comment.getIdx())
                .writerIdx(writer.getIdx())
                .writerRole(writer.getRole().getName())
                .writerName(writer.getName())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .isMine(isMine)
                .emojis(emojiResponses)
                .replies(replyResponses)
                .build();

        commentResponses.add(commentResponse);
    }

    return commentResponses;
}

    private List<EmojiResponse> getEmojiResponses(Comment comment, Long userIdx) {
        // 댓글에 대한 이모지 정보 가져오기
        List<EmojiResponse> emojiResponses = new ArrayList<>();
        List<Interaction> interactions = interactionRepository.findAllByComment(comment);

        // 이모지별로 사용자와 이모지 정보를 조합하여 반환
        Map<Long, EmojiResponse> emojiMap = new HashMap<>();
        for (Interaction interaction : interactions) {
            Emoji emoji = interaction.getEmoji();
            if (!emojiMap.containsKey(emoji.getIdx())) {
                emojiMap.put(emoji.getIdx(), EmojiResponse.builder()
                        .idx(emoji.getIdx())
                        .count(0)
                        .isMine(false)  // default as false
                        .users(new ArrayList<>())
                        .build());
            }

            EmojiResponse emojiResponse = emojiMap.get(emoji.getIdx());
            emojiResponse.setCount(emojiResponse.getCount() + 1);
            if (interaction.getUser().getIdx().equals(userIdx)) {
                emojiResponse.setIsMine(true);  // 이모지를 작성한 사용자가 현재 댓글의 작성자라면 isMine은 true
            }

            emojiResponse.getUsers().add(UserResponse.builder()
                    .idx(interaction.getUser().getIdx())
                    .name(interaction.getUser().getName())
                    .build());
        }

        emojiResponses.addAll(emojiMap.values());
        return emojiResponses;
    }

    private List<ReplyResponse> getReplyResponses(Comment comment, Long userIdx) {
        // 댓글에 대한 답글 정보 가져오기
        List<ReplyResponse> replyResponses = new ArrayList<>();
        for (Comment reply : comment.getReplies()) {
            User replyWriter = reply.getUser();
            Boolean isMine = replyWriter.getIdx().equals(userIdx);

            // 답글에 대한 이모지 정보
            List<EmojiResponse> emojiResponses = getEmojiResponses(reply,userIdx);

            ReplyResponse replyResponse = ReplyResponse.builder()
                    .idx(reply.getIdx())
                    .writerIdx(replyWriter.getIdx())
                    .writerRole(replyWriter.getRole().getName())
                    .writerName(replyWriter.getName())
                    .createdAt(reply.getCreatedAt())
                    .content(reply.getContent())
                    .isMine(isMine)
                    .emojis(emojiResponses)
                    .build();

            replyResponses.add(replyResponse);
        }

        return replyResponses;
    }
}
