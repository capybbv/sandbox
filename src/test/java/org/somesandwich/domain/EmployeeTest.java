package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.DepartmentTestSamples.*;
import static org.somesandwich.domain.EmployeeTestSamples.*;
import static org.somesandwich.domain.EmployeeTestSamples.*;
import static org.somesandwich.domain.JobHistoryTestSamples.*;
import static org.somesandwich.domain.JobTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setEmployeeId(employee1.getEmployeeId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void subEmployeesTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        employee.addSubEmployees(employeeBack);
        assertThat(employee.getSubEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getManager()).isEqualTo(employee);

        employee.removeSubEmployees(employeeBack);
        assertThat(employee.getSubEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getManager()).isNull();

        employee.subEmployees(new HashSet<>(Set.of(employeeBack)));
        assertThat(employee.getSubEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getManager()).isEqualTo(employee);

        employee.setSubEmployees(new HashSet<>());
        assertThat(employee.getSubEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getManager()).isNull();
    }

    @Test
    void jobHistoriesTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        JobHistory jobHistoryBack = getJobHistoryRandomSampleGenerator();

        employee.addJobHistories(jobHistoryBack);
        assertThat(employee.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getEmployee()).isEqualTo(employee);

        employee.removeJobHistories(jobHistoryBack);
        assertThat(employee.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getEmployee()).isNull();

        employee.jobHistories(new HashSet<>(Set.of(jobHistoryBack)));
        assertThat(employee.getJobHistories()).containsOnly(jobHistoryBack);
        assertThat(jobHistoryBack.getEmployee()).isEqualTo(employee);

        employee.setJobHistories(new HashSet<>());
        assertThat(employee.getJobHistories()).doesNotContain(jobHistoryBack);
        assertThat(jobHistoryBack.getEmployee()).isNull();
    }

    @Test
    void managedDepartmentsTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.addManagedDepartments(departmentBack);
        assertThat(employee.getManagedDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getManager()).isEqualTo(employee);

        employee.removeManagedDepartments(departmentBack);
        assertThat(employee.getManagedDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getManager()).isNull();

        employee.managedDepartments(new HashSet<>(Set.of(departmentBack)));
        assertThat(employee.getManagedDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getManager()).isEqualTo(employee);

        employee.setManagedDepartments(new HashSet<>());
        assertThat(employee.getManagedDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getManager()).isNull();
    }

    @Test
    void jobTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Job jobBack = getJobRandomSampleGenerator();

        employee.setJob(jobBack);
        assertThat(employee.getJob()).isEqualTo(jobBack);

        employee.job(null);
        assertThat(employee.getJob()).isNull();
    }

    @Test
    void managerTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        employee.setManager(employeeBack);
        assertThat(employee.getManager()).isEqualTo(employeeBack);

        employee.manager(null);
        assertThat(employee.getManager()).isNull();
    }

    @Test
    void departmentTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.setDepartment(departmentBack);
        assertThat(employee.getDepartment()).isEqualTo(departmentBack);

        employee.department(null);
        assertThat(employee.getDepartment()).isNull();
    }
}
