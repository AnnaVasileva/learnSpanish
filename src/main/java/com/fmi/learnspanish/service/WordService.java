package com.fmi.learnspanish.service;

import com.fmi.learnspanish.web.rest.resource.FlashcardResource;

import java.util.List;

public interface WordService {

  List<FlashcardResource> getCards(String categoryType, int lessonNumber);

}
