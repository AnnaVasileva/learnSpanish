// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStatisticsResource {

	private String username;

	private String mainLevel;

	private String grammarLevel;

	private String vocabularyLevel;

	private String practiceLevel;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grammarLevel == null) ? 0 : grammarLevel.hashCode());
		result = prime * result + ((mainLevel == null) ? 0 : mainLevel.hashCode());
		result = prime * result + ((practiceLevel == null) ? 0 : practiceLevel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((vocabularyLevel == null) ? 0 : vocabularyLevel.hashCode());
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

		UserStatisticsResource other = (UserStatisticsResource) obj;
		if (grammarLevel == null) {
			if (other.grammarLevel != null) {
				return false;
			}
		} else if (!grammarLevel.equals(other.grammarLevel)) {
			return false;
		}

		if (mainLevel == null) {
			if (other.mainLevel != null) {
				return false;
			}
		} else if (!mainLevel.equals(other.mainLevel)) {
			return false;
		}

		if (practiceLevel == null) {
			if (other.practiceLevel != null) {
				return false;
			}
		} else if (!practiceLevel.equals(other.practiceLevel)) {
			return false;
		}

		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}

		if (vocabularyLevel == null) {
			if (other.vocabularyLevel != null) {
				return false;
			}
		} else if (!vocabularyLevel.equals(other.vocabularyLevel)) {
			return false;
		}

		return true;
	}

}
