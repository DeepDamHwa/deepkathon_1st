package com.example.deepkathon_1.repository;

import com.example.deepkathon_1.model.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdx(Long postIdx);
}
