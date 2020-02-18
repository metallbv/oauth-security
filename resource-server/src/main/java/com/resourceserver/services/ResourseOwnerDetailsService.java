package com.resourceserver.services;

import com.resourceserver.entity.ResourceOwner;
import com.resourceserver.entity.Role;
import com.resourceserver.repository.ResourceOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ResourseOwnerDetailsService implements UserDetailsService {

	@Autowired
	private ResourceOwnerRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<ResourceOwner> resourceOwner = repository.findByUsername(username);

		resourceOwner
				.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

		Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

		for (Role role: resourceOwner.get().getRoles()) {
			grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new User(resourceOwner.get().getUsername(),
				resourceOwner.get().getPassword(), grantedAuthoritySet);
	}
}
