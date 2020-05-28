package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.GrammarLevelRepository;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;
import com.fmi.learnspanish.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrammarServiceImpl implements GrammarService {

  private static final int DEFAULT_LESSON = 1;
  private static final String READ_MODE = "r";

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private GrammarLevelRepository grammarLevelRepository;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public GrammarLevel createGrammarLevel() {
    Lesson lesson = lessonRepository.findByLessonNumber(DEFAULT_LESSON);
    GrammarLevel grammarLevel = new GrammarLevel();
    grammarLevel.setLesson(lesson);
    grammarLevel.setLevel(lesson.getLessonNumber());
    grammarLevelRepository.save(grammarLevel);
    return grammarLevel;
  }

  @Override
  public void setLessonGrammar(HttpSession session, int lessonNumber) {
    log.info("Start reading content of lesson " + lessonNumber + "....");

    long startTime = System.nanoTime();
    Lesson lesson = lessonRepository.findByLessonNumber(lessonNumber);
    String path = lesson.getContent();

    try {
      RandomAccessFile lessonContent = new RandomAccessFile(path, READ_MODE);
      FileChannel inChannel = lessonContent.getChannel();
      MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());

      buffer.load();

      CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
      String read = charBuffer.toString();

      session.setAttribute("currentLessonNumber", lessonNumber);
      session.setAttribute("currentLessonTitle", lesson.getTitle());
      session.setAttribute("currentLessonContent", read);

      buffer.clear();
      inChannel.close();
      lessonContent.close();

    } catch (IOException ioe) {
      log.error("Failed while reading content of lesson " + lessonNumber);
      ioe.printStackTrace();
    }

    long endTime = System.nanoTime();
    long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
    log.info("Total elapsed time for reading lesson content: " + elapsedTimeInMillis + " ms");
  }

  @Override
  public void grammarUp(HttpSession session) {
    User user = userRepository.findByEmail((String) session.getAttribute("email"));

    int nextLessonNumber = user.getGrammarLevel().getLevel() + 1;
    Lesson nextLesson = lessonRepository.findByLessonNumber(nextLessonNumber);

    if (Objects.nonNull(nextLesson)) {
      user.getGrammarLevel().setLevel(nextLessonNumber);
      user.getGrammarLevel().setLesson(nextLesson);
    }

    userRepository.saveAndFlush(user);
    sessionService.setSessionAttributes(session, user);
  }

}