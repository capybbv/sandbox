package org.somesandwich.web.rest.payload.request;

import lombok.Data;

@Data
public class CreateDocumentRequest {

    private String documentName;

    private Long documentTypeId;

    private Long employeeId;
}
