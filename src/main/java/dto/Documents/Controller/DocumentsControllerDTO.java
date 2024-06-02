package dto.Documents.Controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DocumentsControllerDTO(long idDocument,
                                     long idAuthor,
                                     String authorDisplayed,
                                     String title,
                                     String description,
                                     String lincTextDocument,
                                     LocalDate dateCreate,
                                     boolean isPublic) {
}
