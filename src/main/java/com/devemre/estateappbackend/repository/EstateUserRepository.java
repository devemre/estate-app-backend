package com.devemre.estateappbackend.repository;

import com.devemre.estateappbackend.entity.EstateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateUserRepository extends JpaRepository<EstateUser, Integer> {

    EstateUser findByEmail(String email);
}
