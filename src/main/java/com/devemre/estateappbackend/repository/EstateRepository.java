package com.devemre.estateappbackend.repository;

import com.devemre.estateappbackend.entity.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Integer> {

    List<Estate> findByEstateUserId(int estateUserId);

}
