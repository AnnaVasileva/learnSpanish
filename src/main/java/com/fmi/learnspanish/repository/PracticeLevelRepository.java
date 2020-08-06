// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmi.learnspanish.domain.PracticeLevel;

@Repository
public interface PracticeLevelRepository extends JpaRepository<PracticeLevel, String> {

}
