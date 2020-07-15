package com.fmi.learnspanish.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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

	@Column(name = "MAIN_LEVEL")
	@Enumerated(EnumType.STRING)
	private MainLevel level;

	@Column(name = "LESSON_NUMBER")
	private int lessonNumber;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = { CascadeType.ALL })
	private Collection<Word> words;

	@OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = { CascadeType.ALL })
	private Collection<Question> questions;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lessonNumber;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Lesson other = (Lesson) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (lessonNumber != other.lessonNumber || level != other.level) {
			return false;
		}

		return true;
	}
}
