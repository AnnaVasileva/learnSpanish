package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyCategory;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.domain.VocabularyStatus;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.repository.VocabularyCategoryRepository;
import com.fmi.learnspanish.repository.VocabularyLevelRepository;
import com.fmi.learnspanish.service.SessionService;
import com.fmi.learnspanish.service.VocabularyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpSession;

@Service
public class VocabularyServiceImpl implements VocabularyService {

  private static final int DEFAULT_LESSON = 1;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private VocabularyLevelRepository vocabularyLevelRepository;

  @Autowired
  private VocabularyCategoryRepository vocabularyCategoryRepository;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public VocabularyLevel createVocabularyLevel() {
    Lesson lesson = lessonRepository.findByLessonNumber(DEFAULT_LESSON);

    VocabularyLevel vocabularyLevel = new VocabularyLevel();
    vocabularyLevel.setLesson(lesson);
    vocabularyLevel.setLevel(lesson.getLessonNumber());
    vocabularyLevelRepository.saveAndFlush(vocabularyLevel);

    setCategories(vocabularyLevel);
    vocabularyLevelRepository.save(vocabularyLevel);

    return vocabularyLevel;
  }

  private void setCategories(VocabularyLevel vocabularyLevel) {
    VocabularyCategory vocabularyCategoryPictures =
        createVocabularyCategory(VocabularyCategoryType.PICTURES, vocabularyLevel);
    vocabularyCategoryPictures.setStatus(VocabularyStatus.FINISHED);
    vocabularyCategoryRepository.save(vocabularyCategoryPictures);

    VocabularyCategory vocabularyCategoryTranslations =
        createVocabularyCategory(VocabularyCategoryType.TRANSLATIONS, vocabularyLevel);

    VocabularyCategory vocabularyCategoryAntonyms =
        createVocabularyCategory(VocabularyCategoryType.ANTONYMS, vocabularyLevel);

    Collection<VocabularyCategory> categories = new ArrayList<>();
    categories.add(vocabularyCategoryPictures);
    categories.add(vocabularyCategoryTranslations);
    categories.add(vocabularyCategoryAntonyms);

    vocabularyLevel.setCategories(categories);
  }

  private VocabularyCategory createVocabularyCategory(VocabularyCategoryType categoryType,
      VocabularyLevel vocabularyLevel) {
    VocabularyCategory vocabularyCategory = new VocabularyCategory();
    vocabularyCategory.setCatagoryType(categoryType);
    vocabularyCategory.setVocabularyLevel(vocabularyLevel);
    vocabularyCategoryRepository.save(vocabularyCategory);
    return vocabularyCategory;
  }

  @Override
  public void categoryUpdate(HttpSession session, String currentCategory) {
    statusUpdate(session, currentCategory);
  }

  private void statusUpdate(HttpSession session, String currentCategory) {
    String email = (String) session.getAttribute("email");
    User user = userRepository.findByEmail(email);

    Collection<VocabularyCategory> categories = user.getVocabularyLevel().getCategories();
    categories.stream()//
        .filter(category -> category.getCatagoryType().toString().equalsIgnoreCase(currentCategory))//
        .forEach(category -> {
          switch (category.getCatagoryType()) {
          case PICTURES:
            category.setStatus(VocabularyStatus.FINISHED);
            session.setAttribute("picturesStatus", VocabularyStatus.FINISHED);
            break;
          case TRANSLATIONS:
            category.setStatus(VocabularyStatus.FINISHED);
            session.setAttribute("translationsStatus", VocabularyStatus.FINISHED);
            break;
          case ANTONYMS:
            category.setStatus(VocabularyStatus.FINISHED);
            session.setAttribute("antonymsStatus", VocabularyStatus.FINISHED);
            break;
          }

        });

    vocabularyLevelUp(session, user, categories);
    userRepository.saveAndFlush(user);
  }

  private void vocabularyLevelUp(HttpSession session, User user, Collection<VocabularyCategory> categories) {
    boolean allSubLevelsFinished =
        categories.stream().allMatch(category -> category.getStatus().equals(VocabularyStatus.FINISHED));
    if (allSubLevelsFinished) {
      categories.forEach(category -> category.setStatus(VocabularyStatus.IN_PROGRESS));

      int nextLessonNumber = user.getVocabularyLevel().getLevel() + 1;
      Lesson nextLesson = lessonRepository.findByLessonNumber(nextLessonNumber);

      if (Objects.nonNull(nextLesson)) {
        user.getVocabularyLevel().setLesson(nextLesson);
        user.getVocabularyLevel().setLevel(nextLessonNumber);
      }

      sessionService.setSessionAttributes(session, user);
    }
  }

}
