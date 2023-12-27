package org.somesandwich.service.dto;

public class UpdateJobEmployeeDTO {

    private String jobId;
    private Long departmentId;
    private Long salary;

    public UpdateJobEmployeeDTO(String jobId, Long departmentId, Long salary) {
        this.jobId = jobId;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public UpdateJobEmployeeDTO() {}

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
