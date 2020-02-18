package com.client.authorizationcode.controllers;

import com.client.authorizationcode.user.ListUsersProfile;
import com.client.authorizationcode.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserPanel {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/userpanel")
	public ModelAndView userpanel() {

		ModelAndView mv = new ModelAndView("userpanel");
		tryToGetUserProfile(mv);

		return mv;
	}

	@GetMapping("/adminprofile")
	public ModelAndView getAdminprofile() {

		ModelAndView mv = new ModelAndView("userpanel");
		tryToGetAdminProfile(mv);

		return mv;
	}

	@GetMapping("/getAllProfiles")
	public ModelAndView getAllProfiles() {

		ModelAndView mv = new ModelAndView("listprofile");
		tryToGetAllUserProfile(mv);

		return mv;
	}

	private void tryToGetAdminProfile(ModelAndView mv) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String endpoint = "http://localhost:8081/api/adminprofile";
		try {
			UserProfile userProfile =
					restTemplate.getForObject(endpoint, UserProfile.class);
			mv.addObject("profile", userProfile);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile");
		}
	}

	private void tryToGetUserProfile(ModelAndView mv) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String endpoint = "http://localhost:8081/api/profile";
		try {
			UserProfile userProfile =
					restTemplate.getForObject(endpoint, UserProfile.class);
			mv.addObject("profile", userProfile);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile");
		}
	}

	private void tryToGetAllUserProfile(ModelAndView mv) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String endpoint = "http://localhost:8081/api/users";
		try {
			ListUsersProfile listUserProfile =
					restTemplate.getForObject(endpoint, ListUsersProfile.class);
			mv.addObject("profile", listUserProfile);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile");
		}
	}

	@GetMapping("/callback")
	public ModelAndView callback() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return new ModelAndView("forward:/adminprofile");
	}
}
