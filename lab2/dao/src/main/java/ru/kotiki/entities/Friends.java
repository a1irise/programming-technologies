package ru.kotiki.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "friends", schema = "public", catalog = "kotiki")
public class Friends {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "kotik_1_id", nullable = false)
    private long kotik1Id;

    @Column(name = "kotik_2_id", nullable = false)
    private long kotik2Id;

    protected Friends() {

    }

    public Friends(long kotik1Id, long kotik2Id) {
        this.kotik1Id = kotik1Id;
        this.kotik2Id = kotik2Id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKotik1Id() {
        return kotik1Id;
    }

    public void setKotik1Id(long kotik1Id) {
        this.kotik1Id = kotik1Id;
    }

    public long getKotik2Id() {
        return kotik2Id;
    }

    public void setKotik2Id(long kotik2Id) {
        this.kotik2Id = kotik2Id;
    }
}
