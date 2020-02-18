package com.oauthserver.repository;

import com.oauthserver.entity.ResourceOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResourceOwnerRepository extends CrudRepository<ResourceOwner, Long> {

	Optional<ResourceOwner> findByUsername(String username);

}
