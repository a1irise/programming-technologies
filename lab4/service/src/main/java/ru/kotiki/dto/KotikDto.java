package ru.kotiki.dto;

import ru.kotiki.entities.Color;

import java.time.LocalDate;
import java.util.Objects;

public class KotikDto {

    private long id;
    private String name;
    private LocalDate dateOfBirth;
    private String breed;
    private Color color;
    private long ownerId;

    public KotikDto() {

    }

    public KotikDto(String name, LocalDate dateOfBirth, String breed, Color color, long ownerId) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
