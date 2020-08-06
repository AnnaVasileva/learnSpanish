// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.fmi.learnspanish.web.resource.FlashcardResource;

public interface WordService {

  List<FlashcardResource> getCards(HttpSession session, String categoryType, int lessonNumber);

}
