package com.udea.p2.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "doc", nullable = false, unique = true)
    private String doc;

    @NotNull
    @Column(name = "program", nullable = false)
    private String program;

    @NotNull
    @Column(name = "program_code", nullable = false)
    private String program_code;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Student name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc() {
        return this.doc;
    }

    public Student doc(String doc) {
        this.setDoc(doc);
        return this;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getProgram() {
        return this.program;
    }

    public Student program(String program) {
        this.setProgram(program);
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgram_code() {
        return this.program_code;
    }

    public Student program_code(String program_code) {
        this.setProgram_code(program_code);
        return this;
    }

    public void setProgram_code(String program_code) {
        this.program_code = program_code;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", doc='" + getDoc() + "'" +
            ", program='" + getProgram() + "'" +
            ", program_code='" + getProgram_code() + "'" +
            "}";
    }
}
