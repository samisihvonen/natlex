package com.example.demo.service;

import com.example.demo.model.Geological;
import com.example.demo.model.Section;
import com.example.demo.repository.SectionRepository;
import com.example.demo.repository.GeologicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private GeologicalRepository geologicalRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }
    public List<Section> getSectionsByGeologicalCode(String code) {
        return sectionRepository.findSectionsByGeologicalCode(code);
    }

    public Section saveOrUpdateSection(Section section) {
        return sectionRepository.save(section);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    public List<Geological> getAllGeologicals() {
        return geologicalRepository.findAll();
    }

    public Optional<Geological> getGeologicalById(Long id) {
        return geologicalRepository.findById(id);
    }

    public Geological saveOrUpdateGeological(Geological geological) {
        return geologicalRepository.save(geological);
    }

    public void deleteGeological(Long id) {
        geologicalRepository.deleteById(id);
    }

    public List<Geological> getAllGeologicales(Long id) {
        return geologicalRepository.findAllById(id);
    }

    public List<Section> getGeologicalByCode(String code) {
        return sectionRepository.findSectionsByGeologicalCode(code);
    }
}
