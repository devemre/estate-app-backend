package com.devemre.estateappbackend.repository;

import com.devemre.estateappbackend.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
