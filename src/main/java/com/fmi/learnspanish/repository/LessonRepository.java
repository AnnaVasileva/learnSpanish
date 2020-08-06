// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

  Lesson findByLevelAndLessonNumber(MainLevel level, int lessonNumber);

}
