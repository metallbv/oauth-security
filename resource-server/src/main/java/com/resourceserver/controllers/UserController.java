package com.resourceserver.controllers;

import com.resourceserver.api.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

	@CrossOrigin
	@RequestMapping("/profile")
	public ResponseEntity<UserProfile> profile() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = user.getUsername() + "@gmail.com";

		UserProfile userProfile = new UserProfile();
		userProfile.setName(user.getUsername());
		userProfile.setEmail(email);

		return ResponseEntity.ok(userProfile);
	}

	@CrossOrigin
	//@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT')")
	@RequestMapping("/adminprofile")
	public ResponseEntity<UserProfile> adminprofile() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = user.getUsername() + "@gmail.com";

		UserProfile userProfile = new UserProfile();
		userProfile.setName(user.getUsername());
		userProfile.setEmail(email);

		return ResponseEntity.ok(userProfile);
	}
}
