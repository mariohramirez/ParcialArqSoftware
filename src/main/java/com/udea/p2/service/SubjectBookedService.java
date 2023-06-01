package com.udea.p2.service;

import com.udea.p2.domain.SubjectBooked;
import com.udea.p2.service.dto.subjectBookedGrades;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SubjectBooked}.
 */
public interface SubjectBookedService {
    /**
     * Save a subjectBooked.
     *
     * @param subjectBooked the entity to save.
     * @return the persisted entity.
     */
    SubjectBooked save(SubjectBooked subjectBooked);

    /**
     * Updates a subjectBooked.
     *
     * @param subjectBooked the entity to update.
     * @return the persisted entity.
     */
    SubjectBooked update(SubjectBooked subjectBooked);

    /**
     * Partially updates a subjectBooked.
     *
     * @param subjectBooked the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubjectBooked> partialUpdate(SubjectBooked subjectBooked);

    /**
     * Get all the subjectBookeds.
     *
     * @return the list of entities.
     */
    List<SubjectBooked> findAll();

    /**
     * Get the "id" subjectBooked.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectBooked> findOne(Long id);

    /**
     * Delete the "id" subjectBooked.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<subjectBookedGrades> getBookedSubjectsByStudentID(String id);

    SubjectBooked setGrades(Long id, subjectBookedGrades grades);
}
