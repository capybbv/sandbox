package org.somesandwich.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import java.util.List;
import org.somesandwich.domain.Document;
import org.somesandwich.repository.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

public interface DocumentSearchRepository extends ElasticsearchRepository<Document, Long>, DocumentSearchRepositoryInternal {}

interface DocumentSearchRepositoryInternal {
    Page<Document> search(String query, Pageable pageable);

    Page<Document> search(Query query);

    @Async
    void index(Document entity);

    @Async
    void deleteFromIndexById(String id);
}

class DocumentSearchRepositoryInternalImpl implements DocumentSearchRepositoryInternal {

    private final DocumentRepository documentRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    DocumentSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, DocumentRepository documentRepository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.documentRepository = documentRepository;
    }

    @Override
    public Page<Document> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<Document> search(Query query) {
        SearchHits<Document> searchHits = elasticsearchTemplate.search(query, Document.class);
        List<Document> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    @Async
    public void index(Document entity) {
        documentRepository.findById(entity.getDocumentId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    @Async
    public void deleteFromIndexById(String id) {
        elasticsearchTemplate.delete(String.valueOf(id), Document.class);
    }
}
