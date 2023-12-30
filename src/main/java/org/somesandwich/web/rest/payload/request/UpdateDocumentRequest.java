package org.somesandwich.web.rest.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDocumentRequest {

    private String documentName;

    private Long documentTypeId;
}
