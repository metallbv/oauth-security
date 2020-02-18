package com.resourceserver.repository;

import com.resourceserver.entity.ResourceOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResourceOwnerRepository extends CrudRepository<ResourceOwner, Long> {

	Optional<ResourceOwner> findByUsername(String username);

}
