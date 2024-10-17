package com.explorer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explorer.entities.UserAuthority;
import com.explorer.entities.UserAuthorityId;

public interface UserAuthorityDAO extends JpaRepository<UserAuthority, UserAuthorityId> {
}