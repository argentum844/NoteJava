package dto.Documents.View;

import lombok.Builder;


@Builder
public record DocumentsViewDTO(long idDocument,
                               long idAuthor,
                               String authorDisplayed,
                               String title,
                               String description,
                               String lincTextDocument,
                               String dateCreate,
                               String isPublic) {
}
