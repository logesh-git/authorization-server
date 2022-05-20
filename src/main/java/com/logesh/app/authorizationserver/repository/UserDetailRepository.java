package com.logesh.app.authorizationserver.repository;

import com.logesh.app.authorizationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<User,Integer> {


    User findByUsername(String name);

}
