package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.VocabularyCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyCategoryRepository extends JpaRepository<VocabularyCategory, String> {

}
