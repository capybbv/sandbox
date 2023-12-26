package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.EmployeeTestSamples.*;
import static org.somesandwich.domain.JobHistoryTestSamples.*;
import static org.somesandwich.domain.JobTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class JobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Job.class);
        Job job1 = getJobSample1();
        Job job2 = new Job();
        assertThat(job1).isNotEqualTo(job2);

        job2.setJobId(job1.getJobId());
        assertThat(job1).isEqualTo(job2);

        job2 = getJobSample2();
        assertThat(job1).isNotEqualTo(job2);
    }

    @Test
    void employeesTest() throws Exception {
        Job job = getJobRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        job.addEmployees(employeeBack);
        assertThat(job.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getJob()).isEqualTo(job);

        job.removeEmployees(employeeBack);
        assertThat(job.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getJob()).isNull();

        job.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(job.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getJob()).isEqualTo(job);

        job.setEmployees(new HashSet<>());
        assertThat(job.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getJob()).isNull();
    }

    @Test
    void jobHistoriesTest() throws Exception {
        Job job = getJobRandomSampleGenerator();
        JobHistory jobHistoryBack = getJobHistoryRandomSampleGenerator();

        job.addJobHistories(jobHistoryBack);
        assertThat(job.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getJob()).isEqualTo(job);

        job.removeJobHistories(jobHistoryBack);
        assertThat(job.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getJob()).isNull();

        job.jobHistories(new HashSet<>(Set.of(jobHistoryBack)));
        assertThat(job.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getJob()).isEqualTo(job);

        job.setJobHistories(new HashSet<>());
        assertThat(job.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getJob()).isNull();
    }
}
