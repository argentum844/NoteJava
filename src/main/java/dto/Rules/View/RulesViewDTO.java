package dto.Rules.View;

import lombok.Builder;

@Builder
public record RulesViewDTO(long idRules, String textRules) {
}
