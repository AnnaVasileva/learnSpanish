// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.GrammarLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarLevelRepository extends JpaRepository<GrammarLevel, String> {

}
