package com.ahmet.repository;

import com.ahmet.repository.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile,String> {

    boolean existsByUserprofileid(Long id); // checks for the existence of a UserProfile entity with a specific userprofileid.

    Optional<UserProfile> findOptionalByAuthid(Long authid);
}
