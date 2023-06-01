package com.udea.p2.web.rest;

import com.udea.p2.domain.SubjectBooked;
import com.udea.p2.repository.SubjectBookedRepository;
import com.udea.p2.service.SubjectBookedService;
import com.udea.p2.service.dto.subjectBookedGrades;
import com.udea.p2.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.p2.domain.SubjectBooked}.
 */
@RestController
@RequestMapping("/api")
public class SubjectBookedResource {

    private final Logger log = LoggerFactory.getLogger(SubjectBookedResource.class);

    private static final String ENTITY_NAME = "subjectBooked";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubjectBookedService subjectBookedService;

    private final SubjectBookedRepository subjectBookedRepository;

    public SubjectBookedResource(SubjectBookedService subjectBookedService, SubjectBookedRepository subjectBookedRepository) {
        this.subjectBookedService = subjectBookedService;
        this.subjectBookedRepository = subjectBookedRepository;
    }

    /**
     * {@code POST  /subject-bookeds} : Create a new subjectBooked.
     *
     * @param subjectBooked the subjectBooked to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectBooked, or with status {@code 400 (Bad Request)} if the subjectBooked has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subject-bookeds")
    public ResponseEntity<SubjectBooked> createSubjectBooked(@RequestBody SubjectBooked subjectBooked) throws URISyntaxException {
        log.debug("REST request to save SubjectBooked : {}", subjectBooked);
        if (subjectBooked.getId() != null) {
            throw new BadRequestAlertException("A new subjectBooked cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectBooked result = subjectBookedService.save(subjectBooked);
        return ResponseEntity
            .created(new URI("/api/subject-bookeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subject-bookeds/:id} : Updates an existing subjectBooked.
     *
     * @param id the id of the subjectBooked to save.
     * @param subjectBooked the subjectBooked to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectBooked,
     * or with status {@code 400 (Bad Request)} if the subjectBooked is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subjectBooked couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subject-bookeds/{id}")
    public ResponseEntity<SubjectBooked> updateSubjectBooked(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubjectBooked subjectBooked
    ) throws URISyntaxException {
        log.debug("REST request to update SubjectBooked : {}, {}", id, subjectBooked);
        if (subjectBooked.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subjectBooked.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subjectBookedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubjectBooked result = subjectBookedService.update(subjectBooked);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subjectBooked.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subject-bookeds/:id} : Partial updates given fields of an existing subjectBooked, field will ignore if it is null
     *
     * @param id the id of the subjectBooked to save.
     * @param subjectBooked the subjectBooked to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectBooked,
     * or with status {@code 400 (Bad Request)} if the subjectBooked is not valid,
     * or with status {@code 404 (Not Found)} if the subjectBooked is not found,
     * or with status {@code 500 (Internal Server Error)} if the subjectBooked couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subject-bookeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubjectBooked> partialUpdateSubjectBooked(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubjectBooked subjectBooked
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubjectBooked partially : {}, {}", id, subjectBooked);
        if (subjectBooked.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subjectBooked.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subjectBookedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubjectBooked> result = subjectBookedService.partialUpdate(subjectBooked);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subjectBooked.getId().toString())
        );
    }

    /**
     * {@code GET  /subject-bookeds} : get all the subjectBookeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjectBookeds in body.
     */
    @GetMapping("/subject-bookeds")
    public List<SubjectBooked> getAllSubjectBookeds() {
        log.debug("REST request to get all SubjectBookeds");
        return subjectBookedService.findAll();
    }

    @GetMapping("/subject-booked-grades/{id}")
    public List<subjectBookedGrades> getBookedsByStudentID(@PathVariable String id) {
        log.debug("REST request to get all SubjectBookeds");
        return subjectBookedService.getBookedSubjectsByStudentID(id);
    }

    @PostMapping("/subject-booked/set-grades/{id}")
    public SubjectBooked setGrades(@PathVariable Long id, @RequestBody subjectBookedGrades subjectGrades) {
        log.debug("REST request to get all SubjectBookeds");
        return subjectBookedService.setGrades(id, subjectGrades);
    }

    /**
     * {@code GET  /subject-bookeds/:id} : get the "id" subjectBooked.
     *
     * @param id the id of the subjectBooked to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectBooked, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subject-bookeds/{id}")
    public ResponseEntity<SubjectBooked> getSubjectBooked(@PathVariable Long id) {
        log.debug("REST request to get SubjectBooked : {}", id);
        Optional<SubjectBooked> subjectBooked = subjectBookedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subjectBooked);
    }

    /**
     * {@code DELETE  /subject-bookeds/:id} : delete the "id" subjectBooked.
     *
     * @param id the id of the subjectBooked to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subject-bookeds/{id}")
    public ResponseEntity<Void> deleteSubjectBooked(@PathVariable Long id) {
        log.debug("REST request to delete SubjectBooked : {}", id);
        subjectBookedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
