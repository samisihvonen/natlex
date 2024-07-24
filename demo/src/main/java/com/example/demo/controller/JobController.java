package com.example.demo.controller;

import com.example.demo.model.Job;
import com.example.demo.model.Section;
import com.example.demo.service.JobService;
import com.example.demo.service.SectionService;
import com.example.demo.util.ExcelService;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@RestController
@EnableAsync
@RequestMapping("/api")
public class JobController {

    private static final Logger logger = Logger.getLogger(JobController.class.getName());

    @Autowired
    private JobService jobService;

    @Autowired
    private SectionService sectionService;

    @PostMapping("/import")
    public ResponseEntity<Long> importFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received request to import file");
        Job job = jobService.createJob("IMPORT");
        jobService.importJob(job.getId(), file);
        return new ResponseEntity<>(job.getId(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/import/{id}")
    public ResponseEntity<String> getImportStatus(@PathVariable Long id) {
        Optional<Job> job = jobService.getJob(id);
        if (job.isPresent()) {
            return ResponseEntity.ok(job.get().getStatus());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/export")
    public ResponseEntity<Long> exportFile() {
        Job job = jobService.createJob("EXPORT");
        jobService.exportJob(job.getId(),  null);
        return new ResponseEntity<>(job.getId(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<String> getExportStatus(@PathVariable Long id) {
        Optional<Job> job = jobService.getJob(id);
        if (job.isPresent()) {
            return ResponseEntity.ok(job.get().getStatus());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/export/{id}/file")
    public void downloadExportedFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Job> job = jobService.getJob(id);
        if (job.isPresent() && "DONE".equals(job.get().getStatus())) {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String timeToString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String headerValue = "attachment; filename=sections" + timeToString + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List <Section> listOfSections = sectionService.getAllSections();
            ExcelService generator = new ExcelService(listOfSections);
            generator.generateExcelFile(response);
        } else if (job.isPresent() && "IN PROGRESS".equals(job.get().getStatus())) {
            throw new IllegalStateException("Exporting is still in process");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
