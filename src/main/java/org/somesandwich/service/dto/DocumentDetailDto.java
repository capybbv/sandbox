package org.somesandwich.service.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DocumentDetailDto implements Serializable {

    private Long documentId;

    private String DocumentName;

    private DocumentTypeDto documentType;
}
