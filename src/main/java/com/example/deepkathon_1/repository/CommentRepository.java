package com.example.deepkathon_1.repository;

import com.example.deepkathon_1.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query()
    List<Comment> findAllByUserIdxAndPostIdx(Long userIdx, Long postIdx);
}
