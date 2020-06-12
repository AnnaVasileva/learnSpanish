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
	
	// Temp solution. When practice logic is ready this should be changed to
	// String type.
	private Integer practiceLevel;
}
