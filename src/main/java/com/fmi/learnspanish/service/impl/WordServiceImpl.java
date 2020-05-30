package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.Word;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.WordRepository;
import com.fmi.learnspanish.service.WordService;
import com.fmi.learnspanish.web.rest.resource.FlashcardResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private WordRepository wordRepository;

  @Override
  public List<FlashcardResource> getCards(String categoryType, int lessonNumber) {
    Lesson lesson = lessonRepository.findByLessonNumber(lessonNumber);
    List<Word> words = wordRepository.findAllByLessonId(lesson.getId());
    List<FlashcardResource> flashcards = new ArrayList<>();

    words.stream().forEach(word -> {
      FlashcardResource flashcard = new FlashcardResource();
      flashcard.setFront(word.getName());

      if (categoryType.equalsIgnoreCase(VocabularyCategoryType.PICTURES.toString())) {
        flashcard.setBack(word.getPicture());
      } else if (categoryType.equalsIgnoreCase(VocabularyCategoryType.TRANSLATIONS.toString())) {
        flashcard.setBack(word.getTranslation());
      } else if (categoryType.equalsIgnoreCase(VocabularyCategoryType.ANTONYMS.toString())) {
        flashcard.setBack(word.getAntonym());
      }

      flashcards.add(flashcard);
    });

    return flashcards;
  }

}
