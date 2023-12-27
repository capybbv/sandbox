package org.somesandwich.repository;

import java.time.Instant;
import java.util.Optional;
import org.somesandwich.domain.Employee;
import org.somesandwich.domain.JobHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
    public Optional<JobHistory> findJobHistoryByEmployeeAndStartDate(Employee employee, Instant startDate);
}
