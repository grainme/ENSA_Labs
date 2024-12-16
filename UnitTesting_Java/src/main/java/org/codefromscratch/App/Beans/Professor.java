package org.codefromscratch.App.Beans;

import java.util.Objects;

public class Professor {
    private Long id;
    private String name;
    private String specialite;

    public Professor() {
    }

    public Professor(Long id, String name, String specialite) {
        setId(id);
        setName(name);
        setSpecialite(specialite);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        if (specialite == null || specialite.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialite cannot be null or empty");
        }
        this.specialite = specialite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(id, professor.id) && Objects.equals(name, professor.name) && Objects.equals(specialite, professor.specialite);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialite='" + specialite + '\'' +
                '}';
    }
}