package com.example.deepkathon_1.repository;

import com.example.deepkathon_1.model.entity.Comment;
import com.example.deepkathon_1.model.entity.Interaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {
    List<Interaction> findAllByComment(Comment comment);
}
