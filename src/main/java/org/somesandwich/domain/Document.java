package org.somesandwich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "document_id")
    @org.springframework.data.annotation.Id
    private Long documentId;

    @Column(name = "document_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String documentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents" }, allowSetters = true)
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents" }, allowSetters = true)
    private Employee employee;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Document)) return false;

        return documentId != null && documentId.equals(((Document) obj).documentId);
    }

    @Override
    public String toString() {
        return (
            "Document{" +
            "documentId=" +
            documentId +
            ", documentName='" +
            documentName +
            '\'' +
            ", documentType=" +
            documentType +
            ", employee=" +
            employee +
            '}'
        );
    }
}
