package org.somesandwich.repository;

import org.somesandwich.domain.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {}
