// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmi.learnspanish.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String>{

	List<Question> findAllByLessonId(String lessonNumber);
}
