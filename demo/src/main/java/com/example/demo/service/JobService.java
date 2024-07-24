package com.example.demo.service;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(String type) {
        Job job = new Job();
        job.setType(type);
        job.setStatus("IN PROGRESS");
        return jobRepository.save(job);
    }
    
    public Optional<Job> getJob(Long jobId) {
        return jobRepository.findById(jobId);
    }

    public void updateJob(Long jobId, String status) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setStatus(status);
            jobRepository.save(job);
        }
    }

    @Async
    public void importJob(Long jobId, MultipartFile file) {
        try {
            Thread.sleep(5000); 
            updateJob(jobId, "DONE");
        } catch (Exception e) {
            updateJob(jobId, "ERROR");
        }
    }

    @Async
    public void exportJob(Long jobId, HttpServletResponse response) {
        try {
            Thread.sleep(5000); 
            updateJob(jobId, "DONE");
        } catch (Exception e) {
            updateJob(jobId, "ERROR");
        }
    }
}
