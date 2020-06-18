package com.fmi.learnspanish.service;

import java.util.List;

import com.fmi.learnspanish.web.rest.resource.MakeAdminResource;
import com.fmi.learnspanish.web.rest.resource.UserStatisticsResource;

public interface AdminService {

	List<UserStatisticsResource> getUsersStatistics();

	void makeAdmin(MakeAdminResource makeAdminResource);
}
