package com.udea.p2.service;

import com.udea.p2.domain.Subject;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Subject}.
 */
public interface SubjectService {
    /**
     * Save a subject.
     *
     * @param subject the entity to save.
     * @return the persisted entity.
     */
    Subject save(Subject subject);

    /**
     * Updates a subject.
     *
     * @param subject the entity to update.
     * @return the persisted entity.
     */
    Subject update(Subject subject);

    /**
     * Partially updates a subject.
     *
     * @param subject the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Subject> partialUpdate(Subject subject);

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    List<Subject> findAll();

    /**
     * Get the "id" subject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Subject> findOne(Long id);

    /**
     * Delete the "id" subject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
