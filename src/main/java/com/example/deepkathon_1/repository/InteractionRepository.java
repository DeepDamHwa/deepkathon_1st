package com.example.deepkathon_1.repository;

import com.example.deepkathon_1.model.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {
}
