package org.somesandwich.web.rest.vm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.DocumentType;
import org.somesandwich.service.DocumentTypeService;
import org.somesandwich.web.rest.payload.request.CreateDocumentTypeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeResource {

    private static final String ENTITY_NAME = "documentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Logger log = LoggerFactory.getLogger(DocumentTypeResource.class);
    private final DocumentTypeService documentTypeService;

    public DocumentTypeResource(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @PostMapping("")
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody CreateDocumentTypeRequest request) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", request);
        //        if (request.getDocumentTypeName() != null) {
        //            throw new BadRequestAlertException("A new documentType cannot already have an ID",
        //                ENTITY_NAME, "idexists");
        //        }
        DocumentType result = documentTypeService.save(request);
        return ResponseEntity
            .created(new URI("/api/document-types/" + result.getDocumentTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getDocumentTypeId().toString()))
            .body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<DocumentType>> getAllDocumentType(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all DocumentType");
        Page<DocumentType> result = documentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }
}
