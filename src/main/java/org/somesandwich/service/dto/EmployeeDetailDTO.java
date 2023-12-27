package org.somesandwich.service.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.somesandwich.domain.Document;

/**
 * A DTO detailing an employee.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EmployeeDetailDTO {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String jobId;
    private String jobTitle;
    private Long salary;
    private Long commissionPct;
    private Long managerId;
    private Long departmentId;
    private String departmentName;
    private String locationId;
    private Set<Document> documents;
}
