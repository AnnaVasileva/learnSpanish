package com.fmi.learnspanish.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QUESTION")
@Getter
@Setter
@NoArgsConstructor
public class Question {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(length = 36)
	private String id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "LESSON_ID", referencedColumnName = "ID")
	protected Lesson lesson;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "CORRECT_ANSWER")
	private String correctAnswer;

	@Column(name = "WRONG_OPTION_1")
	private String wrongOption1;

	@Column(name = "WRONG_OPTION_2")
	private String wrongOption2;

	@Column(name = "WRONG_OPTION_3")
	private String wrongOption3;

}
