package com.fmi.learnspanish.web.rest.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStatisticsResource {

	private String username;

	private String grammarLevel;

	private String vocabularyLevel;
	
	private String practiceLevel;
}
