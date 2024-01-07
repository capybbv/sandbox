package org.somesandwich.service;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Employee;
import org.somesandwich.domain.JobHistory;
import org.somesandwich.repository.EmployeeRepository;
import org.somesandwich.repository.JobHistoryRepository;
import org.somesandwich.repository.JobRepository;
import org.somesandwich.repository.search.EmployeeSearchRepository;
import org.somesandwich.repository.search.JobHistorySearchRepository;
import org.somesandwich.service.dto.UpdateJobEmployeeDTO;
import org.somesandwich.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.somesandwich.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final JobHistoryRepository jobHistoryRepository;
    private final JobHistorySearchRepository jobHistorySearchRepository;

    private final JobRepository jobRepository;
    private final DepartmentService departmentService;

    private final EmployeeSearchRepository employeeSearchRepository;

    public EmployeeService(
        EmployeeRepository employeeRepository,
        JobHistoryRepository jobHistoryRepository,
        JobHistorySearchRepository jobHistorySearchRepository,
        JobRepository jobRepository,
        DepartmentService departmentService,
        EmployeeSearchRepository employeeSearchRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistorySearchRepository = jobHistorySearchRepository;
        this.jobRepository = jobRepository;
        this.departmentService = departmentService;
        this.employeeSearchRepository = employeeSearchRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        //before save

        log.debug("Request to save Employee : {}", employee);
        Employee result = employeeRepository.save(employee);
        employeeSearchRepository.index(result);
        return result;
    }

    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Transactional(rollbackFor = { SQLException.class, BadRequestAlertException.class })
    public Employee update(Employee employee) {
        log.debug("Request to update Employee : {}", employee);
        Employee existing_employee = employeeRepository.findById(employee.getEmployeeId()).get();

        LocalDate now = LocalDate.now();
        if (
            employee.getDepartment() != existing_employee.getDepartment() ||
            employee.getJob() != existing_employee.getJob() ||
            !Objects.equals(employee.getSalary(), existing_employee.getSalary())
        ) {
            if (jobHistoryRepository.findJobHistoryByEmployeeAndStartDate(existing_employee, existing_employee.getHireDate()).isPresent()) {
                throw new BadRequestAlertException("Employee is still working", ENTITY_NAME, "stillworking");
            }
            JobHistory jobHis = new JobHistory()
                .employee(existing_employee)
                .employeeId(existing_employee.getEmployeeId())
                .startDate(existing_employee.getHireDate())
                .endDate(Instant.now())
                .job(existing_employee.getJob())
                .department(existing_employee.getDepartment());
            jobHistoryRepository.save(jobHis);
            // jobHistorySearchRepository.index(jobHis);
        }
        Employee result = employeeRepository.save(employee);
        employeeSearchRepository.index(result);
        return result;
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getEmployeeId())
            .map(existingEmployee -> {
                if (employee.getFirstName() != null) {
                    existingEmployee.setFirstName(employee.getFirstName());
                }
                if (employee.getLastName() != null) {
                    existingEmployee.setLastName(employee.getLastName());
                }
                if (employee.getEmail() != null) {
                    existingEmployee.setEmail(employee.getEmail());
                }
                if (employee.getPhoneNumber() != null) {
                    existingEmployee.setPhoneNumber(employee.getPhoneNumber());
                }
                if (employee.getHireDate() != null) {
                    existingEmployee.setHireDate(employee.getHireDate());
                }
                if (employee.getSalary() != null) {
                    existingEmployee.setSalary(employee.getSalary());
                }
                if (employee.getCommissionPct() != null) {
                    existingEmployee.setCommissionPct(employee.getCommissionPct());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(savedEmployee -> {
                employeeSearchRepository.index(savedEmployee);
                return savedEmployee;
            });
    }

    @Transactional(rollbackFor = { SQLException.class, BadRequestAlertException.class })
    public Optional<Employee> updateJob(Long empId, UpdateJobEmployeeDTO dto) {
        log.debug("Request to partially update Employee : {}", empId);

        AtomicBoolean isUpdate = new AtomicBoolean(false);
        Optional<Employee> emp = employeeRepository.findById(empId);

        if (emp.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", "Employee", "idnotfound");
        }

        JobHistory jobHis = new JobHistory()
            .employee(emp.get())
            .startDate(emp.get().getHireDate())
            .endDate(Instant.from(java.time.LocalDate.now()))
            .job(emp.get().getJob())
            .department(emp.get().getDepartment());

        if (dto.getJobId() != null) {
            Optional<org.somesandwich.domain.Job> job = jobRepository.findById(dto.getJobId());

            if (job.isEmpty()) {
                throw new BadRequestAlertException("Entity not found", "Job", "idnotfound");
            }
            if (!Objects.equals(emp.get().getJob().getJobId(), dto.getJobId())) {
                isUpdate.set(true);
                emp.get().setJob(job.get());
            }
        }

        if (dto.getDepartmentId() != null) {
            Optional<org.somesandwich.domain.Department> department = departmentService.findOne(dto.getDepartmentId());

            if (department.isEmpty()) {
                throw new BadRequestAlertException("Entity not found", "Department", "idnotfound");
            }

            if (!Objects.equals(emp.get().getDepartment().getDepartmentId(), dto.getDepartmentId())) {
                isUpdate.set(true);
                emp.get().setDepartment(department.get());
            }
        }

        if (dto.getSalary() != null && !Objects.equals(dto.getSalary(), emp.get().getSalary())) {
            isUpdate.set(true);
            emp.get().setSalary(dto.getSalary());
        }

        if (isUpdate.get()) {
            emp.get().setHireDate(Instant.from(java.time.LocalDate.now()));
            employeeRepository.save(emp.get());
            employeeSearchRepository.index(emp.get());
            jobHistoryRepository.save(jobHis);
        }

        return emp;
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
        employeeSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the employee corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Employees for query {}", query);
        return employeeSearchRepository.search(query, pageable);
    }
}
