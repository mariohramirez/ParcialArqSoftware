package com.udea.p2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.p2.IntegrationTest;
import com.udea.p2.domain.SubjectBooked;
import com.udea.p2.repository.SubjectBookedRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubjectBookedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubjectBookedResourceIT {

    private static final LocalDate DEFAULT_DATE_ENROLL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENROLL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GRADES = "AAAAAAAAAA";
    private static final String UPDATED_GRADES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subject-bookeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubjectBookedRepository subjectBookedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubjectBookedMockMvc;

    private SubjectBooked subjectBooked;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectBooked createEntity(EntityManager em) {
        SubjectBooked subjectBooked = new SubjectBooked().dateEnroll(DEFAULT_DATE_ENROLL).grades(DEFAULT_GRADES);
        return subjectBooked;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectBooked createUpdatedEntity(EntityManager em) {
        SubjectBooked subjectBooked = new SubjectBooked().dateEnroll(UPDATED_DATE_ENROLL).grades(UPDATED_GRADES);
        return subjectBooked;
    }

    @BeforeEach
    public void initTest() {
        subjectBooked = createEntity(em);
    }

    @Test
    @Transactional
    void createSubjectBooked() throws Exception {
        int databaseSizeBeforeCreate = subjectBookedRepository.findAll().size();
        // Create the SubjectBooked
        restSubjectBookedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectBooked)))
            .andExpect(status().isCreated());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeCreate + 1);
        SubjectBooked testSubjectBooked = subjectBookedList.get(subjectBookedList.size() - 1);
        assertThat(testSubjectBooked.getDateEnroll()).isEqualTo(DEFAULT_DATE_ENROLL);
        assertThat(testSubjectBooked.getGrades()).isEqualTo(DEFAULT_GRADES);
    }

    @Test
    @Transactional
    void createSubjectBookedWithExistingId() throws Exception {
        // Create the SubjectBooked with an existing ID
        subjectBooked.setId(1L);

        int databaseSizeBeforeCreate = subjectBookedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectBookedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectBooked)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubjectBookeds() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        // Get all the subjectBookedList
        restSubjectBookedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectBooked.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnroll").value(hasItem(DEFAULT_DATE_ENROLL.toString())))
            .andExpect(jsonPath("$.[*].grades").value(hasItem(DEFAULT_GRADES)));
    }

    @Test
    @Transactional
    void getSubjectBooked() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        // Get the subjectBooked
        restSubjectBookedMockMvc
            .perform(get(ENTITY_API_URL_ID, subjectBooked.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subjectBooked.getId().intValue()))
            .andExpect(jsonPath("$.dateEnroll").value(DEFAULT_DATE_ENROLL.toString()))
            .andExpect(jsonPath("$.grades").value(DEFAULT_GRADES));
    }

    @Test
    @Transactional
    void getNonExistingSubjectBooked() throws Exception {
        // Get the subjectBooked
        restSubjectBookedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubjectBooked() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();

        // Update the subjectBooked
        SubjectBooked updatedSubjectBooked = subjectBookedRepository.findById(subjectBooked.getId()).get();
        // Disconnect from session so that the updates on updatedSubjectBooked are not directly saved in db
        em.detach(updatedSubjectBooked);
        updatedSubjectBooked.dateEnroll(UPDATED_DATE_ENROLL).grades(UPDATED_GRADES);

        restSubjectBookedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubjectBooked.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubjectBooked))
            )
            .andExpect(status().isOk());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
        SubjectBooked testSubjectBooked = subjectBookedList.get(subjectBookedList.size() - 1);
        assertThat(testSubjectBooked.getDateEnroll()).isEqualTo(UPDATED_DATE_ENROLL);
        assertThat(testSubjectBooked.getGrades()).isEqualTo(UPDATED_GRADES);
    }

    @Test
    @Transactional
    void putNonExistingSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subjectBooked.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectBooked))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectBooked))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectBooked)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubjectBookedWithPatch() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();

        // Update the subjectBooked using partial update
        SubjectBooked partialUpdatedSubjectBooked = new SubjectBooked();
        partialUpdatedSubjectBooked.setId(subjectBooked.getId());

        partialUpdatedSubjectBooked.grades(UPDATED_GRADES);

        restSubjectBookedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubjectBooked.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubjectBooked))
            )
            .andExpect(status().isOk());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
        SubjectBooked testSubjectBooked = subjectBookedList.get(subjectBookedList.size() - 1);
        assertThat(testSubjectBooked.getDateEnroll()).isEqualTo(DEFAULT_DATE_ENROLL);
        assertThat(testSubjectBooked.getGrades()).isEqualTo(UPDATED_GRADES);
    }

    @Test
    @Transactional
    void fullUpdateSubjectBookedWithPatch() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();

        // Update the subjectBooked using partial update
        SubjectBooked partialUpdatedSubjectBooked = new SubjectBooked();
        partialUpdatedSubjectBooked.setId(subjectBooked.getId());

        partialUpdatedSubjectBooked.dateEnroll(UPDATED_DATE_ENROLL).grades(UPDATED_GRADES);

        restSubjectBookedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubjectBooked.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubjectBooked))
            )
            .andExpect(status().isOk());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
        SubjectBooked testSubjectBooked = subjectBookedList.get(subjectBookedList.size() - 1);
        assertThat(testSubjectBooked.getDateEnroll()).isEqualTo(UPDATED_DATE_ENROLL);
        assertThat(testSubjectBooked.getGrades()).isEqualTo(UPDATED_GRADES);
    }

    @Test
    @Transactional
    void patchNonExistingSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subjectBooked.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectBooked))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectBooked))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubjectBooked() throws Exception {
        int databaseSizeBeforeUpdate = subjectBookedRepository.findAll().size();
        subjectBooked.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectBookedMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subjectBooked))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubjectBooked in the database
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubjectBooked() throws Exception {
        // Initialize the database
        subjectBookedRepository.saveAndFlush(subjectBooked);

        int databaseSizeBeforeDelete = subjectBookedRepository.findAll().size();

        // Delete the subjectBooked
        restSubjectBookedMockMvc
            .perform(delete(ENTITY_API_URL_ID, subjectBooked.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubjectBooked> subjectBookedList = subjectBookedRepository.findAll();
        assertThat(subjectBookedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
