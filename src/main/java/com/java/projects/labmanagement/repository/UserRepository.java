package com.java.projects.labmanagement.repository;

import com.java.projects.labmanagement.entity.Role;
import com.java.projects.labmanagement.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(Role role);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
