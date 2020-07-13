package com.fmi.learnspanish.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.Word;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.WordRepository;
import com.fmi.learnspanish.service.WordService;
import com.fmi.learnspanish.web.resource.FlashcardResource;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private WordRepository wordRepository;

	@Override
	public List<FlashcardResource> getCards(HttpSession session, String categoryType, int lessonNumber) {

		MainLevel level = (MainLevel) session.getAttribute("level");
		Lesson lesson = lessonRepository.findByLevelAndLessonNumber(level, lessonNumber);
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
