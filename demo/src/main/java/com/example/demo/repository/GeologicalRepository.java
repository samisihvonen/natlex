package com.example.demo.repository;

import com.example.demo.model.Geological;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeologicalRepository extends JpaRepository<Geological, Long> {
    List<Geological> findByCode(String code);

    List<Geological> findAllById(Long id);
}
