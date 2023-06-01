package com.udea.p2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.p2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubjectBookedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectBooked.class);
        SubjectBooked subjectBooked1 = new SubjectBooked();
        subjectBooked1.setId(1L);
        SubjectBooked subjectBooked2 = new SubjectBooked();
        subjectBooked2.setId(subjectBooked1.getId());
        assertThat(subjectBooked1).isEqualTo(subjectBooked2);
        subjectBooked2.setId(2L);
        assertThat(subjectBooked1).isNotEqualTo(subjectBooked2);
        subjectBooked1.setId(null);
        assertThat(subjectBooked1).isNotEqualTo(subjectBooked2);
    }
}
