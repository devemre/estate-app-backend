package com.devemre.estateappbackend.controller;

import com.devemre.estateappbackend.entity.Estate;
import com.devemre.estateappbackend.exception.EstateNotFoundException;
import com.devemre.estateappbackend.repository.EstateRepository;
import com.devemre.estateappbackend.repository.EstateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EstateController {

    private EstateRepository estateRepository;
    private EstateUserRepository estateUserRepository;

    @Autowired
    public EstateController(EstateRepository estateRepository, EstateUserRepository estateUserRepository) {
        this.estateRepository = estateRepository;
        this.estateUserRepository = estateUserRepository;
    }

    @GetMapping("/estates")
    public List<Estate> getEstates() {
        return estateRepository.findAll();
    }

    @GetMapping("/estates/{estateId}")
    public Estate getEstatesByUserId(@PathVariable int estateId) {
        Optional<Estate> estate = estateRepository.findById(estateId);
        if (estate.isPresent()) {
            return estate.get();
        }
        throw new EstateNotFoundException("Estate with id " + estateId + " not found");
    }
}
