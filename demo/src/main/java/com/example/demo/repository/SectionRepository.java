package com.example.demo.repository;

import com.example.demo.model.Section;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT DISTINCT s FROM Section s JOIN s.geologicalClasses g WHERE g.code = :code")
    List<Section> findSectionsByGeologicalCode(@Param("code") String code);
}
