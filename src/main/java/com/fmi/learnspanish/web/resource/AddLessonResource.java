// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.resource;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddLessonResource {

	private String level;

	private int lessonNumber;

	private String title;

	private MultipartFile content;
}
