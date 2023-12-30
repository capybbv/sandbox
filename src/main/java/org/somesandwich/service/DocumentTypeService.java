package org.somesandwich.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.DocumentType;
import org.somesandwich.repository.DocumentTypeRepository;
import org.somesandwich.repository.search.DocumentTypeSearchRepository;
import org.somesandwich.web.rest.payload.request.CreateDocumentTypeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeService.class);

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeSearchRepository documentTypeSearchRepository;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository, DocumentTypeSearchRepository documentTypeSearchRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeSearchRepository = documentTypeSearchRepository;
    }

    public DocumentType save(CreateDocumentTypeRequest request) {
        log.debug("Request to save DocumentType : {}", request);
        DocumentType documentType = DocumentType.builder().documentTypeName(request.getDocumentTypeName()).build();
        DocumentType result = documentTypeRepository.save(documentType);
        documentTypeSearchRepository.index(result);
        log.debug("Saved DocumentType : {}", result);
        return result;
    }

    public DocumentType update(Long documentTypeId, CreateDocumentTypeRequest request) {
        log.debug("Request to update DocumentType : {}", documentTypeId);
        return documentTypeRepository
            .findById(documentTypeId)
            .map(existsDocumentType -> {
                existsDocumentType.setDocumentTypeName(request.getDocumentTypeName());
                DocumentType result = documentTypeRepository.save(existsDocumentType);
                documentTypeSearchRepository.index(result);
                log.debug("Updated DocumentType : {}", result);
                return result;
            })
            .orElseThrow(() -> new RuntimeException("DocumentType not found with id " + documentTypeId));
    }

    public Page<DocumentType> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepository.findAll(pageable);
    }

    public DocumentType findOne(Long documentTypeId) {
        log.debug("Request to get DocumentType : {}", documentTypeId);
        return documentTypeRepository
            .findById(documentTypeId)
            .orElseThrow(() -> new RuntimeException("DocumentType not found with id " + documentTypeId));
    }
}
