package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.Lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

  Lesson findByLessonNumber(int lessonNumber);

}
