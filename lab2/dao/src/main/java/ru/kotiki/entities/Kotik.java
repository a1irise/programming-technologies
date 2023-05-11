package ru.kotiki.entities;

import jakarta.persistence.*;
import ru.kotiki.utils.ColorConverter;

import java.time.LocalDate;

@Entity
@Table(name = "kotik", schema = "public", catalog = "kotiki")
public class Kotik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Convert(converter = ColorConverter.class)
    @Column(name = "color", nullable = false)
    private Color color;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    protected Kotik() {

    }

    public Kotik(String name, LocalDate dateOfBirth, String breed, Color color, long ownerId) {
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
