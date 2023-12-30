package org.somesandwich.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import java.util.List;
import org.somesandwich.domain.DocumentType;
import org.somesandwich.repository.DocumentTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentTypeSearchRepository extends ElasticsearchRepository<DocumentType, String>, DocumentTypeSearchRepositoryInternal {}

interface DocumentTypeSearchRepositoryInternal {
    Page<DocumentType> search(String query, Pageable pageable);

    Page<DocumentType> search(Query query);

    void index(DocumentType documentType);

    void deleteFromIndexById(String id);
}

class DocumentTypeSearchRepositoryInternalImpl implements DocumentTypeSearchRepositoryInternal {

    private final DocumentTypeRepository repository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    DocumentTypeSearchRepositoryInternalImpl(DocumentTypeRepository repository, ElasticsearchTemplate elasticsearchTemplate) {
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<DocumentType> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<DocumentType> search(Query query) {
        SearchHits<DocumentType> searchHits = elasticsearchTemplate.search(query, DocumentType.class);
        List<DocumentType> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(DocumentType documentType) {
        repository.findById(documentType.getDocumentTypeId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(String id) {
        elasticsearchTemplate.delete(id, DocumentType.class);
    }
}
