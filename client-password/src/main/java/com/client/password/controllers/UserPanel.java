package com.client.password.controllers;

import com.client.password.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserPanel {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	@Autowired
	private AccessTokenRequest accessTokenRequest;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/callback")
	public ModelAndView callback() {
		return new ModelAndView("forward:/userpanel");
	}

	@GetMapping("/userpanel")
	public ModelAndView userpanel() {

		ModelAndView mv = new ModelAndView("userpanel");

		tryToGetUserProfile(mv);

		return mv;

	}

	private void tryToGetUserProfile(ModelAndView mv) {
		accessTokenRequest.add("username", "user1");
		accessTokenRequest.add("password", "123");

		String endpoint = "http://localhost:8081/api/profile";
		try {
			UserProfile userProfile =
					restTemplate.getForObject(endpoint, UserProfile.class);
			mv.addObject("profile", userProfile);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile");
		}
	}
}
