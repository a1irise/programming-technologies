package ru.kotiki.dto;

import java.time.LocalDate;
import java.util.Objects;

public class OwnerDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;

    public OwnerDto() {

    }

    public OwnerDto(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
