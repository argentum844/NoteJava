package dto.Rules.Controller;

import lombok.Builder;

@Builder
public record RulesControllerDTO(long idRules, String textRules) {
}
