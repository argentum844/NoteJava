package dto.Editors.View;

import lombok.Builder;

@Builder
public record EditorsViewDTO(long idEditor, long idDocument) {
}
