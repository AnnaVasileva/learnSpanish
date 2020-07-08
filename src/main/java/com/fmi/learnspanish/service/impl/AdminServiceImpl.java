package com.fmi.learnspanish.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AdminService;
import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<UserStatisticsResource> getUsersStatistics() {
		List<User> users = userRepository.findAllByOrderByUsername();
		List<UserStatisticsResource> usersStatisticsList = new ArrayList<>();
		users.forEach(user -> {
			UserStatisticsResource userStatisticsResource = new UserStatisticsResource();
			userStatisticsResource.setUsername(user.getUsername());
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
