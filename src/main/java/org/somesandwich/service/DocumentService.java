package org.somesandwich.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Document;
import org.somesandwich.domain.DocumentType;
import org.somesandwich.repository.DocumentRepository;
import org.somesandwich.repository.DocumentTypeRepository;
import org.somesandwich.repository.EmployeeRepository;
import org.somesandwich.repository.search.DocumentSearchRepository;
import org.somesandwich.web.rest.payload.request.CreateDocumentRequest;
import org.somesandwich.web.rest.payload.request.UpdateDocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentService.class.getName());
    private final DocumentRepository documentRepository;
    private final DocumentSearchRepository documentSearchRepository;

    private final DocumentTypeRepository documentTypeRepository;
    private final EmployeeRepository employeeRepository;

    public DocumentService(
        DocumentRepository documentRepository,
        DocumentSearchRepository documentSearchRepository,
        DocumentTypeRepository documentTypeRepository,
        EmployeeRepository employeeRepository
    ) {
        this.documentRepository = documentRepository;
        this.documentSearchRepository = documentSearchRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a document.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    public Document save(CreateDocumentRequest request) {
        log.debug("Request to save DocumentType : {}", request);

        Document result = documentRepository.save(
            Document
                .builder()
                .documentName(request.getDocumentName())
                .documentType(documentTypeRepository.findById(request.getDocumentTypeId()).get())
                .employee(employeeRepository.findById(request.getEmployeeId()).get())
                .build()
        );

        documentSearchRepository.index(result);
        return result;
    }

    public Optional<Document> update(Long documentId, UpdateDocumentRequest request) {
        log.debug("Request to update Document : {}", documentId);
        return documentRepository
            .findById(documentId)
            .map(existsDocument -> {
                if (request.getDocumentName() != null && !request.getDocumentName().isEmpty()) {
                    existsDocument.setDocumentName(request.getDocumentName());
                }

                if (request.getDocumentTypeId() != null) {
                    Optional<DocumentType> documentType = documentTypeRepository.findById(request.getDocumentTypeId());
                    documentType.ifPresent(existsDocument::setDocumentType);
                }

                return existsDocument;
            })
            .map(documentRepository::save)
            .map(document -> {
                documentSearchRepository.index(document);
                return document;
            });
    }

    /**
     *
     * Get one document by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Document> findOne(Long id) {
        log.debug("Request to get Document : {}", id);
        return documentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Document> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentRepository.findAll(pageable);
    }

    /**
     * Delete the document by id.
     *
     * @param documentId the id of the entity.
     */
    public void delete(Long documentId) {
        log.debug("Request to delete Document : {}", documentId);
        documentRepository.deleteById(documentId);
        documentSearchRepository.deleteById(documentId);
    }
}
