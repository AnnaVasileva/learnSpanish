// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AdminService;
import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.LessonAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.StatisticsNotFoundException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.AddLessonResource;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	private static final String DIRECTORY_ROOT = "/MasterProject/";
	private static final String FILE_TYPE = ".txt";

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void addLesson(AddLessonResource addLessonResource) throws IOException, LessonAlreadyExistsException {
		String level = addLessonResource.getLevel();
		String title = addLessonResource.getTitle().replace(' ', '_');
		String filePath = DIRECTORY_ROOT + level + "_" + title + FILE_TYPE;
		addNewLesson(addLessonResource, filePath);
		addNewLessonContent(addLessonResource, filePath);
	}

	private void addNewLesson(AddLessonResource addLessonResource, String filePath) throws LessonAlreadyExistsException {
		Lesson newLesson = new Lesson();

		MainLevel level = null;
		if (Objects.isNull(addLessonResource.getLevel())
				|| addLessonResource.getLevel().equalsIgnoreCase(MainLevel.BEGINNER.toString())) {
			level = MainLevel.BEGINNER;
			newLesson.setLevel(level);
		} else {
			level = MainLevel.INTERMIDIATE;
			newLesson.setLevel(level);
		}

		Lesson existingLesson = lessonRepository.findByLevelAndLessonNumber(level, addLessonResource.getLessonNumber());
		if (!Objects.isNull(existingLesson)) {
			throw new LessonAlreadyExistsException("This lesson already exists.");
		}
		newLesson.setLessonNumber(addLessonResource.getLessonNumber());
		newLesson.setTitle(addLessonResource.getTitle());
		newLesson.setContent("d:" + filePath);
		lessonRepository.saveAndFlush(newLesson);
	}

	private void addNewLessonContent(AddLessonResource addLessonResource, String filePath) throws IOException {
		MultipartFile file = addLessonResource.getContent();
		byte[] bytes = file.getBytes();
		Path path = Paths.get(filePath);
		Files.write(path, bytes);
	}

	@Override
	public List<UserStatisticsResource> getUsersStatistics() throws StatisticsNotFoundException {
		List<User> users = userRepository.findAllByOrderByUsername();

		if (Objects.isNull(users)) {
			throw new StatisticsNotFoundException("Sorry, no statistivs are found.");
		}

		List<UserStatisticsResource> usersStatisticsList = new ArrayList<>();
		users.forEach(user -> {
			UserStatisticsResource userStatisticsResource = new UserStatisticsResource();
			userStatisticsResource.setUsername(user.getUsername());

			String mainLevelString = user.getLevel().toString();
			String capitalizedMainLevel = mainLevelString.substring(0, 1) + mainLevelString.substring(1).toLowerCase();
			userStatisticsResource.setMainLevel(capitalizedMainLevel);

			userStatisticsResource.setGrammarLevel(user.getGrammarLevel().getLesson().getTitle());
			userStatisticsResource.setVocabularyLevel(user.getVocabularyLevel().getLesson().getTitle());
			userStatisticsResource.setPracticeLevel(user.getPracticeLevel().getLesson().getTitle());

			usersStatisticsList.add(userStatisticsResource);
		});

		return usersStatisticsList;
	}

	@Override
	public void makeAdmin(MakeAdminResource makeAdminResource)
			throws AdminAlreadyExistsException, UserNotFoundException {
		User user = userRepository.findByUsernameAndEmail(makeAdminResource.getUsername(),
				makeAdminResource.getEmail());

		if (Objects.nonNull(user)) {
			Role adminRole = roleRepository.findByAuthority("ADMIN");

			if (user.getAuthorities().contains(adminRole)) {
				log.warn("User {} with email {}, is already an admin.", makeAdminResource.getUsername(),
						makeAdminResource.getEmail());
				throw new AdminAlreadyExistsException(
						"User " + makeAdminResource.getUsername() + " is already an admin.");
			}

			Set<Role> authorities = new HashSet<>();
			authorities.add(adminRole);
			user.setAuthorities(authorities);
			userRepository.saveAndFlush(user);
		} else {
			log.warn("User {} with email {}, was not found.", makeAdminResource.getUsername(),
					makeAdminResource.getEmail());
			throw new UserNotFoundException("User not found.");
		}

	}
}
