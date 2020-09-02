// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import java.io.IOException;
import java.util.List;

import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.LessonAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.StatisticsNotFoundException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.AddLessonResource;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

public interface AdminService {
	
	void addLesson(AddLessonResource addLessonResource) throws IOException, LessonAlreadyExistsException;

	List<UserStatisticsResource> getUsersStatistics() throws StatisticsNotFoundException;

	void makeAdmin(MakeAdminResource makeAdminResource) throws AdminAlreadyExistsException, UserNotFoundException;
}
