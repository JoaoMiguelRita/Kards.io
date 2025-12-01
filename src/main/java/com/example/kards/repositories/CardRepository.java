package com.example.kards.repositories;

import com.example.kards.domain.DeckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository  extends JpaRepository<DeckEntity, Integer> {
}
