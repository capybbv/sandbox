package org.somesandwich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "employee_id")
    @org.springframework.data.annotation.Id
    private Long employeeId;

    @Column(name = "first_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String firstName;

    @Column(name = "last_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String lastName;

    @Column(name = "email")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String email;

    @Column(name = "phone_number")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "commission_pct")
    private Long commissionPct;

    @OneToOne
    //    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    @Nullable
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(
        value = { "subEmployees", "jobHistories", "managedDepartments", "job", "manager", "department" },
        allowSetters = true
    )
    private Set<Employee> subEmployees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "job", "department", "employee" }, allowSetters = true)
    private Set<JobHistory> jobHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "employees", "jobHistories", "manager", "location" }, allowSetters = true)
    private Set<Department> managedDepartments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "jobHistories" }, allowSetters = true)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "subEmployees", "jobHistories", "managedDepartments", "job", "manager", "department" },
        allowSetters = true
    )
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "jobHistories", "manager", "location" }, allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Employee employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return this.hireDate;
    }

    public Employee hireDate(Instant hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return this.salary;
    }

    public Employee salary(Long salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getCommissionPct() {
        return this.commissionPct;
    }

    public Employee commissionPct(Long commissionPct) {
        this.setCommissionPct(commissionPct);
        return this;
    }

    public void setCommissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Set<Employee> getSubEmployees() {
        return this.subEmployees;
    }

    public void setSubEmployees(Set<Employee> employees) {
        if (this.subEmployees != null) {
            this.subEmployees.forEach(i -> i.setManager(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setManager(this));
        }
        this.subEmployees = employees;
    }

    public Employee subEmployees(Set<Employee> employees) {
        this.setSubEmployees(employees);
        return this;
    }

    public Employee addSubEmployees(Employee employee) {
        this.subEmployees.add(employee);
        employee.setManager(this);
        return this;
    }

    public Employee removeSubEmployees(Employee employee) {
        this.subEmployees.remove(employee);
        employee.setManager(null);
        return this;
    }

    public Set<JobHistory> getJobHistories() {
        return this.jobHistories;
    }

    public void setJobHistories(Set<JobHistory> jobHistories) {
        if (this.jobHistories != null) {
            this.jobHistories.forEach(i -> i.setEmployee(null));
        }
        if (jobHistories != null) {
            jobHistories.forEach(i -> i.setEmployee(this));
        }
        this.jobHistories = jobHistories;
    }

    public Employee jobHistories(Set<JobHistory> jobHistories) {
        this.setJobHistories(jobHistories);
        return this;
    }

    public Employee addJobHistories(JobHistory jobHistory) {
        this.jobHistories.add(jobHistory);
        jobHistory.setEmployee(this);
        return this;
    }

    public Employee removeJobHistories(JobHistory jobHistory) {
        this.jobHistories.remove(jobHistory);
        jobHistory.setEmployee(null);
        return this;
    }

    public Set<Department> getManagedDepartments() {
        return this.managedDepartments;
    }

    public void setManagedDepartments(Set<Department> departments) {
        if (this.managedDepartments != null) {
            this.managedDepartments.forEach(i -> i.setManager(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setManager(this));
        }
        this.managedDepartments = departments;
    }

    public Employee managedDepartments(Set<Department> departments) {
        this.setManagedDepartments(departments);
        return this;
    }

    public Employee addManagedDepartments(Department department) {
        this.managedDepartments.add(department);
        department.setManager(this);
        return this;
    }

    public Employee removeManagedDepartments(Department department) {
        this.managedDepartments.remove(department);
        department.setManager(null);
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employee job(Job job) {
        this.setJob(job);
        return this;
    }

    public Employee getManager() {
        return this.manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public Employee manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getEmployeeId() != null && getEmployeeId().equals(((Employee) o).getEmployeeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "employeeId=" + getEmployeeId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", commissionPct=" + getCommissionPct() +
            "}";
    }
}
