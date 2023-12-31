package org.somesandwich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@org.springframework.data.elasticsearch.annotations.Document(indexName = "job")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Job implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "job_id")
    @org.springframework.data.annotation.Id
    private String jobId;

    @Column(name = "job_title")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String jobTitle;

    @Column(name = "min_salary")
    private Long minSalary;

    @Column(name = "max_salary")
    private Long maxSalary;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(
        value = { "subEmployees", "jobHistories", "managedDepartments", "job", "manager", "department" },
        allowSetters = true
    )
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "job", "department", "employee" }, allowSetters = true)
    private Set<JobHistory> jobHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getJobId() {
        return this.jobId;
    }

    public Job jobId(String jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public Job jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Long getMinSalary() {
        return this.minSalary;
    }

    public Job minSalary(Long minSalary) {
        this.setMinSalary(minSalary);
        return this;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public Long getMaxSalary() {
        return this.maxSalary;
    }

    public Job maxSalary(Long maxSalary) {
        this.setMaxSalary(maxSalary);
        return this;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.jobId;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Job setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setJob(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setJob(this));
        }
        this.employees = employees;
    }

    public Job employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Job addEmployees(Employee employee) {
        this.employees.add(employee);
        employee.setJob(this);
        return this;
    }

    public Job removeEmployees(Employee employee) {
        this.employees.remove(employee);
        employee.setJob(null);
        return this;
    }

    public Set<JobHistory> getJobHistories() {
        return this.jobHistories;
    }

    public void setJobHistories(Set<JobHistory> jobHistories) {
        if (this.jobHistories != null) {
            this.jobHistories.forEach(i -> i.setJob(null));
        }
        if (jobHistories != null) {
            jobHistories.forEach(i -> i.setJob(this));
        }
        this.jobHistories = jobHistories;
    }

    public Job jobHistories(Set<JobHistory> jobHistories) {
        this.setJobHistories(jobHistories);
        return this;
    }

    public Job addJobHistories(JobHistory jobHistory) {
        this.jobHistories.add(jobHistory);
        jobHistory.setJob(this);
        return this;
    }

    public Job removeJobHistories(JobHistory jobHistory) {
        this.jobHistories.remove(jobHistory);
        jobHistory.setJob(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return getJobId() != null && getJobId().equals(((Job) o).getJobId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "jobId=" + getJobId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", minSalary=" + getMinSalary() +
            ", maxSalary=" + getMaxSalary() +
            "}";
    }
}
