package com.fmi.learnspanish.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GRAMMAR_LEVEL")
@Getter
@Setter
@NoArgsConstructor
public class GrammarLevel {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 36)
  private String id;

  @Column(name = "GRAMMAR_LEVEL")
  private int level;

  @ManyToOne(optional = false)
  @JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
  private Lesson lesson;

  @OneToOne(mappedBy = "grammarLevel")
  private User user;
}
