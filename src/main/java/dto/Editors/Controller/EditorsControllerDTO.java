package dto.Editors.Controller;

import lombok.Builder;

@Builder
public record EditorsControllerDTO(long idEditor, long idDocument) {
}
