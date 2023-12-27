package org.somesandwich.web.rest.payload.req;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A request for create {@link org.somesandwich.domain.Document} entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentReq implements Serializable {

    private String documentName;
    private Long documentTypeId;
}
