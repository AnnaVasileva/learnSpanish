package com.fmi.learnspanish.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VOCABULARY_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
public class VocabularyCategory {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 36)
  private String id;

  @Column(name = "CATEGORY_TYPE")
  @Enumerated(EnumType.STRING)
  private VocabularyCategoryType catagoryType;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private VocabularyStatus status = VocabularyStatus.IN_PROGRESS;

  @ManyToOne(optional = false)
  @JoinColumn(name = "VOCABULARY_LEVEL_ID", referencedColumnName = "ID")
  private VocabularyLevel vocabularyLevel;
}
