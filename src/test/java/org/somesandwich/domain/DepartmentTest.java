package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.DepartmentTestSamples.*;
import static org.somesandwich.domain.EmployeeTestSamples.*;
import static org.somesandwich.domain.JobHistoryTestSamples.*;
import static org.somesandwich.domain.LocationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class DepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = getDepartmentSample1();
        Department department2 = new Department();
        assertThat(department1).isNotEqualTo(department2);

        department2.setDepartmentId(department1.getDepartmentId());
        assertThat(department1).isEqualTo(department2);

        department2 = getDepartmentSample2();
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    void employeesTest() throws Exception {
        Department department = getDepartmentRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        department.addEmployees(employeeBack);
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.removeEmployees(employeeBack);
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();

        department.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.setEmployees(new HashSet<>());
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();
    }

    @Test
    void jobHistoriesTest() throws Exception {
        Department department = getDepartmentRandomSampleGenerator();
        JobHistory jobHistoryBack = getJobHistoryRandomSampleGenerator();

        department.addJobHistories(jobHistoryBack);
        assertThat(department.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getDepartment()).isEqualTo(department);

        department.removeJobHistories(jobHistoryBack);
        assertThat(department.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getDepartment()).isNull();

        department.jobHistories(new HashSet<>(Set.of(jobHistoryBack)));
        assertThat(department.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getDepartment()).isEqualTo(department);

        department.setJobHistories(new HashSet<>());
        assertThat(department.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getDepartment()).isNull();
    }

    @Test
    void managerTest() throws Exception {
        Department department = getDepartmentRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        department.setManager(employeeBack);
        assertThat(department.getManager()).isEqualTo(employeeBack);

        department.manager(null);
        assertThat(department.getManager()).isNull();
    }

    @Test
    void locationTest() throws Exception {
        Department department = getDepartmentRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        department.setLocation(locationBack);
        assertThat(department.getLocation()).isEqualTo(locationBack);

        department.location(null);
        assertThat(department.getLocation()).isNull();
    }
}
