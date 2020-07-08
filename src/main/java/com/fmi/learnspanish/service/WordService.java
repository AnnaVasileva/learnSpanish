package com.fmi.learnspanish.service;

import java.util.List;

import com.fmi.learnspanish.web.resource.FlashcardResource;

public interface WordService {

  List<FlashcardResource> getCards(String categoryType, int lessonNumber);

}
