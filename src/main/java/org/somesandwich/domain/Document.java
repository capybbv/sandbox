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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    //    public Document(Long documentId, String documentName, DocumentType documentType, Employee employee) {
    //        this.documentId = documentId;
    //        this.documentName = documentName;
    //        this.documentType = documentType;
    //        this.employee = employee;
    //    }
    //
    //    public Document() {}
    //
    //    public Long getDocumentId() {
    //        return documentId;
    //    }
    //
    //    public Document setDocumentId(Long documentId) {
    //        this.documentId = documentId;
    //        return this;
    //    }
    //
    //    public String getDocumentName() {
    //        return documentName;
    //    }
    //
    //    public Document setDocumentName(String documentName) {
    //        this.documentName = documentName;
    //        return this;
    //    }
    //
    //    public DocumentType getDocumentType() {
    //        return documentType;
    //    }
    //
    //    public void setDocumentType(DocumentType documentType) {
    //        if (this.documentType != null) {
    //            this.documentType.getDocuments().remove(this);
    //        }
    //
    //        this.documentType = documentType;
    //
    //        if (this.documentType != null) {
    //            this.documentType.getDocuments().add(this);
    //        }
    //    }
    //
    //    public Employee getEmployee() {
    //        return employee;
    //    }
    //
    //    public void setEmployee(Employee employee) {
    //        if (this.employee != null) {
    //            this.employee.getDocuments().remove(this);
    //        }
    //
    //        this.employee = employee;
    //
    //        if (this.employee != null) {
    //            this.employee.getDocuments().add(this);
    //        }
    //    }

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
