package com.example.deepkathon_1.repository;

import com.example.deepkathon_1.model.entity.Comment;
import com.example.deepkathon_1.model.entity.Emoji;
import com.example.deepkathon_1.model.entity.Interaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {
//    List<Interaction> findAllByIdCommentIdx(Long commentIdx);


    @Query("select i from Interaction i where i.comment = :comment GROUP BY i.emoji")
    List<Interaction> findAllByCommentGroupByEmojiIdx(Comment comment);

    List<Interaction> findByEmojiAndComment(Emoji emoji, Comment comment);
}
