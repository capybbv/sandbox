package org.somesandwich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Document;
import org.somesandwich.service.DocumentService;
import org.somesandwich.web.rest.payload.request.CreateDocumentRequest;
import org.somesandwich.web.rest.payload.request.UpdateDocumentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/documents")
public class DocumentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentResource.class.getName());

    private static final String ENTITY_NAME = "document";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentService documentService;

    public DocumentResource(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("")
    public ResponseEntity<Document> createDocument(@RequestBody CreateDocumentRequest request) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", request);

        Document result = documentService.save(request);

        return ResponseEntity
            .created(new URI("/api/documents/" + result.getDocumentId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getDocumentId().toString()))
            .body(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody UpdateDocumentRequest request)
        throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", id);

        Optional<Document> result = documentService.update(id, request);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<Document>> getAllDocument(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all DocumentType");
        Page<Document> result = documentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Document>> getAllDocumentByEmployeeId(@PathVariable Long employeeId) {
        List<Document> result = documentService.findAllByEmployeeId(employeeId);
        return ResponseEntity.ok().body(result);
    }
}
