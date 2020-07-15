package com.fmi.learnspanish.web.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlashcardResource {

	private String front;

	private String back;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((back == null) ? 0 : back.hashCode());
		result = prime * result + ((front == null) ? 0 : front.hashCode());
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

		FlashcardResource other = (FlashcardResource) obj;
		if (back == null) {
			if (other.back != null) {
				return false;
			}
		} else if (!back.equals(other.back)) {
			return false;
		}

		if (front == null) {
			if (other.front != null) {
				return false;
			}
		} else if (!front.equals(other.front)) {
			return false;
		}

		return true;
	}

}
