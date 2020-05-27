package com.fmi.learnspanish.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WORD")
@Getter
@Setter
@NoArgsConstructor
public class Word {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 36)
  private String id;

  @Column(name = "NAME")
  private String name;

  // @Column(name = "picture")
  // private String picture;

  @Column(name = "TRANSLATION")
  private String translation;

  @Column(name = "ANTONYM")
  private String antonym;

  @ManyToOne(optional = false)
  @JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
  protected Lesson lesson;
}
