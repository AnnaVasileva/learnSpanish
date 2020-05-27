package com.fmi.learnspanish.domain;

import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LESSON")
@Getter
@Setter
@NoArgsConstructor
public class Lesson {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 36)
  private String id;

  @Column(name = "LESSON_NUMBER")
  private int lessonNumber;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "CONTENT")
  private String content;

  @OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = {CascadeType.ALL})
  private Collection<Word> words;

  @OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = {CascadeType.ALL})
  private Collection<GrammarLevel> grammarLevel;

  @OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = {CascadeType.ALL})
  private Collection<VocabularyLevel> vocabularyLevel;

}
