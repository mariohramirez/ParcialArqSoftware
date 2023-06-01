package com.udea.p2.repository;

import com.udea.p2.domain.SubjectBooked;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubjectBooked entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectBookedRepository extends JpaRepository<SubjectBooked, Long> {
    @Query("select sb from SubjectBooked sb where sb.student.doc= :id")
    List<SubjectBooked> GetByBookedByDoc(@Param("id") String ID);
}
