package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.VocabularyLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyLevelRepository extends JpaRepository<VocabularyLevel, String> {

}
