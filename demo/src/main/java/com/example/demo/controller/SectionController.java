package com.example.demo.controller;

import com.example.demo.model.Section;
import com.example.demo.service.SectionService;
import com.example.demo.util.ExcelService;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@EnableAsync
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = sectionService.getAllSections();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }
    @GetMapping("/by-code")
    public ResponseEntity<List<Section>> getSectionsByGeologicalCode(@RequestParam("code") String code) {
        List<Section> sections = sectionService.getGeologicalByCode(code);
        return ResponseEntity.ok(sections);
    }

    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        try {
            Section savedSection = sectionService.saveOrUpdateSection(section);
            return new ResponseEntity<>(savedSection, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception and return an appropriate HTTP status
            System.err.println("Error creating section: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable("id") Long id, @RequestBody Section section) {
        Optional<Section> existingSection = sectionService.getSectionById(id);
        if (existingSection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        section.setId(id);
        Section updatedSection = sectionService.saveOrUpdateSection(section);
        return ResponseEntity.ok(updatedSection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable("id") Long id) {
        Optional<Section> section = sectionService.getSectionById(id);
        if (section.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/export")
    // public void exportIntoExcelFile(HttpServletResponse response) throws InternalException {
    //     System.out.println("Exporting data to excel file >>>>>>");
    //     response.setContentType("application/octet-stream");
    //     String headerKey = "Content-Disposition";
    //     String timeToString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    //     System.out.println("TIME NOW: timeToString: " + timeToString);
    //     String headerValue = "attachment; filename=sections" + timeToString + ".xlsx";
    //     response.setHeader(headerKey, headerValue);
    //     List <Section> listOfSections = sectionService.getAllSections();
    //     System.out.println("listOfSections: " + listOfSections);
    //     ExcelService generator = new ExcelService(listOfSections);
    //     try {
    //         generator.generateExcelFile(response);
    //     } catch (IOException e) {
    //         System.out.println("Error occurred while exporting data to excel file");
    //         e.printStackTrace();
    //     }
    // }
}
