package org.somesandwich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "document_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "document_type_id")
    @org.springframework.data.annotation.Id
    private Long documentTypeId;

    @Column(name = "document_type_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String documentTypeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "employee, documentType" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    //
    //    public DocumentType(Long documentTypeId, String documentTypeName, Set<Document> documents) {
    //        this.documentTypeId = documentTypeId;
    //        this.documentTypeName = documentTypeName;
    //        this.documents = documents;
    //    }
    //
    //    public DocumentType() {}
    //
    //    public Long getDocumentTypeId() {
    //        return this.documentTypeId;
    //    }
    //
    //    public DocumentType setDocumentTypeId(Long documentTypeId) {
    //        this.documentTypeId = documentTypeId;
    //        return this;
    //    }
    //
    //    public String getDocumentTypeName() {
    //        return this.documentTypeName;
    //    }
    //
    //    public DocumentType setDocumentTypeName(String documentTypeName) {
    //        this.documentTypeName = documentTypeName;
    //        return this;
    //    }
    //
    //    public Set<Document> getDocuments() {
    //        return this.documents;
    //    }
    //
    //    public void setDocuments(Set<Document> documents) {
    //        if (this.documents != null) {
    //            this.documents.forEach(i -> i.setDocumentType(null));
    //        }
    //
    //        if (documents != null) {
    //            documents.forEach(i -> i.setDocumentType(this));
    //        }
    //
    //        this.documents = documents;
    //    }

    @Override
    public String toString() {
        return (
            "DocumentType{" +
            "documentTypeId=" +
            documentTypeId +
            ", documentTypeName='" +
            documentTypeName +
            '\'' +
            ", documents=" +
            documents +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentType documentType = (DocumentType) o;

        if (documentType.documentTypeId == null || documentTypeId == null) {
            return false;
        }

        return documentTypeId.equals(documentType.documentTypeId);
    }
}
