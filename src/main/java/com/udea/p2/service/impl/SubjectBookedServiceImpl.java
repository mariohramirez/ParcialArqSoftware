package com.udea.p2.service.impl;

import com.google.gson.Gson;
import com.udea.p2.domain.SubjectBooked;
import com.udea.p2.repository.SubjectBookedRepository;
import com.udea.p2.service.SubjectBookedService;
import com.udea.p2.service.dto.subjectBookedGrades;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubjectBooked}.
 */
@Service
@Transactional
public class SubjectBookedServiceImpl implements SubjectBookedService {

    private final Logger log = LoggerFactory.getLogger(SubjectBookedServiceImpl.class);

    private final SubjectBookedRepository subjectBookedRepository;

    public SubjectBookedServiceImpl(SubjectBookedRepository subjectBookedRepository) {
        this.subjectBookedRepository = subjectBookedRepository;
    }

    @Override
    public SubjectBooked save(SubjectBooked subjectBooked) {
        log.debug("Request to save SubjectBooked : {}", subjectBooked);
        return subjectBookedRepository.save(subjectBooked);
    }

    @Override
    public SubjectBooked update(SubjectBooked subjectBooked) {
        log.debug("Request to update SubjectBooked : {}", subjectBooked);
        return subjectBookedRepository.save(subjectBooked);
    }

    @Override
    public Optional<SubjectBooked> partialUpdate(SubjectBooked subjectBooked) {
        log.debug("Request to partially update SubjectBooked : {}", subjectBooked);

        return subjectBookedRepository
            .findById(subjectBooked.getId())
            .map(existingSubjectBooked -> {
                if (subjectBooked.getDateEnroll() != null) {
                    existingSubjectBooked.setDateEnroll(subjectBooked.getDateEnroll());
                }
                if (subjectBooked.getGrades() != null) {
                    existingSubjectBooked.setGrades(subjectBooked.getGrades());
                }

                return existingSubjectBooked;
            })
            .map(subjectBookedRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectBooked> findAll() {
        log.debug("Request to get all SubjectBookeds");
        return subjectBookedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectBooked> findOne(Long id) {
        log.debug("Request to get SubjectBooked : {}", id);
        return subjectBookedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectBooked : {}", id);
        subjectBookedRepository.deleteById(id);
    }

    @Override
    public List<subjectBookedGrades> getBookedSubjectsByStudentID(String id) {
        List<SubjectBooked> subjects = this.subjectBookedRepository.GetByBookedByDoc(id);
        List<subjectBookedGrades> grades = new ArrayList();
        Gson g = new Gson();
        for (int i = 0; i < subjects.size(); i += 1) {
            try {
                subjectBookedGrades grade = g.fromJson(subjects.get(i).getGrades(), subjectBookedGrades.class);
                grades.add(grade);
            } catch (Exception e) {
                System.out.println("Materia con notas invalidas");
            }
        }
        return grades;
    }

    @Override
    public SubjectBooked setGrades(Long id, subjectBookedGrades grades) {
        try {
            SubjectBooked subject = this.subjectBookedRepository.findById(id).get();
            if (subject == null) {
                return null;
            }
            Gson g = new Gson();
            String GradesJSON = g.toJson(grades);
            subject.setGrades(GradesJSON);
            this.subjectBookedRepository.save(subject);

            return subject;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
