package org.somesandwich.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Document;
import org.somesandwich.domain.DocumentType;
import org.somesandwich.repository.DocumentRepository;
import org.somesandwich.repository.DocumentTypeRepository;
import org.somesandwich.repository.search.DocumentSearchRepository;
import org.somesandwich.repository.search.DocumentTypeSearchRepository;
import org.somesandwich.web.rest.payload.req.CreateDocumentReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    private final DocumentSearchRepository documentSearchRepository;

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeSearchRepository documentTypeSearchRepository;

    public DocumentService(
        DocumentRepository documentRepository,
        DocumentSearchRepository documentSearchRepository,
        DocumentTypeRepository documentTypeRepository,
        DocumentTypeSearchRepository documentTypeSearchRepository
    ) {
        this.documentRepository = documentRepository;
        this.documentSearchRepository = documentSearchRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeSearchRepository = documentTypeSearchRepository;
    }

    public Document save(CreateDocumentReq req) {
        log.debug("Request to save Document : {}", req);
        Optional<DocumentType> documentType = documentTypeSearchRepository.findById(req.getDocumentTypeId());

        if (documentType.isPresent()) {
            throw new RuntimeException("Document type not found");
        }
        Document document = Document
            .builder()
            .documentName(req.getDocumentName())
            .documentType(documentTypeRepository.findById(req.getDocumentTypeId()).get())
            .build();

        Document result = documentRepository.save(document);
        documentSearchRepository.index(result);
        documentSearchRepository.index(result);
        return result;
    }
}
