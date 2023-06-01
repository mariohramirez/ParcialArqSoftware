package com.udea.p2.service.impl;

import com.udea.p2.domain.Subject;
import com.udea.p2.repository.SubjectRepository;
import com.udea.p2.service.SubjectService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subject}.
 */
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject save(Subject subject) {
        log.debug("Request to save Subject : {}", subject);
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject) {
        log.debug("Request to update Subject : {}", subject);
        return subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> partialUpdate(Subject subject) {
        log.debug("Request to partially update Subject : {}", subject);

        return subjectRepository
            .findById(subject.getId())
            .map(existingSubject -> {
                if (subject.getName() != null) {
                    existingSubject.setName(subject.getName());
                }
                if (subject.getCode() != null) {
                    existingSubject.setCode(subject.getCode());
                }
                if (subject.getTeacher() != null) {
                    existingSubject.setTeacher(subject.getTeacher());
                }

                return existingSubject;
            })
            .map(subjectRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findAll() {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subject> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.deleteById(id);
    }
}
