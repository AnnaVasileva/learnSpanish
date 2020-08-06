// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LearnspanishApplication.class);
	}

}
