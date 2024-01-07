package org.somesandwich.repository;

import java.util.ArrayList;
import java.util.List;
import org.somesandwich.domain.Document;
import org.somesandwich.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByEmployee(Employee employee);
}
