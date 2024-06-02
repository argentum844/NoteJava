package dto.Documents.View;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DocumentInsertDTO(long idAuthor,
                                String authorDisplayed,
                                String title,
                                String description,
                                String textDocument,
                                LocalDate dateCreate,
                                boolean isPublic) {
}
