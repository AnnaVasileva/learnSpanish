package com.fmi.learnspanish.web.resource;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResource {

	private String text;

	private Set<String> choices;

	private String answer;
}
