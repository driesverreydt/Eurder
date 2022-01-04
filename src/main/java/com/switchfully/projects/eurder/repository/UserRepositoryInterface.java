package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryInterface extends JpaRepository<User, UUID> {
}
