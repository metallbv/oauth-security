package com.client.authorizationcode.security;

import com.client.authorizationcode.user.ClientUser;
import com.client.authorizationcode.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<ClientUser> optionalUser = userRepository.findByUsername(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("invalid username or password");
		}

		return new ClientUserDetails(optionalUser.get());
	}
}
