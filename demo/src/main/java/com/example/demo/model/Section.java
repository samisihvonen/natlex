package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;


@Entity
public class Section extends SectionImpl {


    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Geological> geologicalClasses;

    // Getters and Setters

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return List<Geological> return the geologicalClasses
     */
    public List<Geological> getGeologicalClasses() {
        return geologicalClasses;
    }

    /**
     * @param geologicalClasses the geologicalClasses to set
     */
    public void setGeologicalClasses(List<Geological> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }

}
