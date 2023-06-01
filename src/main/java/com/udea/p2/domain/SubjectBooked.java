package com.udea.p2.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A SubjectBooked.
 */
@Entity
@Table(name = "subject_booked")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubjectBooked implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_enroll")
    private LocalDate dateEnroll;

    @Column(name = "grades")
    private String grades;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubjectBooked id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEnroll() {
        return this.dateEnroll;
    }

    public SubjectBooked dateEnroll(LocalDate dateEnroll) {
        this.setDateEnroll(dateEnroll);
        return this;
    }

    public void setDateEnroll(LocalDate dateEnroll) {
        this.dateEnroll = dateEnroll;
    }

    public String getGrades() {
        return this.grades;
    }

    public SubjectBooked grades(String grades) {
        this.setGrades(grades);
        return this;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SubjectBooked student(Student student) {
        this.setStudent(student);
        return this;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SubjectBooked subject(Subject subject) {
        this.setSubject(subject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectBooked)) {
            return false;
        }
        return id != null && id.equals(((SubjectBooked) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectBooked{" +
            "id=" + getId() +
            ", dateEnroll='" + getDateEnroll() + "'" +
            ", grades='" + getGrades() + "'" +
            "}";
    }
}
