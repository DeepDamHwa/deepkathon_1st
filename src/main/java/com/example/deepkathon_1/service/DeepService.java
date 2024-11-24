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
        //근데 여기서는 userIdx는 필요없지 않나?
        List<Comment> comments = commentRepository.findAllByUserIdxAndPostIdx(userIdx,postIdx);


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

                boolean isMine = users.contains(userIdx);

                Integer count = users.size();

                emojis.add(inte.getEmoji().toDto(count, isMine, userResponses));

            }

            responses.add(comment.toDto(true, emojis, null));

        }

        return responses;
    }
}

//logging
//GET http://localhost:8080/deep/who/is/winner/1/1
//    select
//            c1_0.idx,
//            c1_0.content,
//            c1_0.created_at,
//            c1_0.modified_at,
//            c1_0.parent_idx,
//            c1_0.post_idx,
//            c1_0.user_idx
//            from
//            comment c1_0
//            where
//            c1_0.user_idx=?
//            and c1_0.post_idx=?


//    select
//            p1_0.idx,
//            p1_0.created_at,
//            p1_0.modified_at,
//            u1_0.idx,
//            u1_0.name,
//            r1_0.idx,
//            r1_0.name
//            from
//            post p1_0
//            left join
//            user u1_0
//            on u1_0.idx=p1_0.user_idx
//            left join
//            role r1_0
//            on r1_0.idx=u1_0.role_idx
//            where
//            p1_0.idx=?

//        select
//        i1_0.idx,
//        i1_0.comment_idx,
//        i1_0.emoji_idx,
//        i1_0.user_idx
//        from
//        interaction i1_0
//        where
//        i1_0.comment_idx=?
//        group by
//        i1_0.emoji_idx

//        select
//        e1_0.idx,
//        e1_0.name
//        from
//        emoji e1_0
//        where
//        e1_0.idx=?

//        select
//        u1_0.idx,
//        u1_0.name,
//        r1_0.idx,
//        r1_0.name
//        from
//        user u1_0
//        left join
//        role r1_0
//        on r1_0.idx=u1_0.role_idx
//        where
//        u1_0.idx=?

//        select
//        i1_0.idx,
//        i1_0.comment_idx,
//        i1_0.emoji_idx,
//        i1_0.user_idx
//        from
//        interaction i1_0
//        where
//        i1_0.emoji_idx=?
//        and i1_0.comment_idx=?

