package mapper;

import dto.Rules.Controller.RulesControllerDTO;
import dto.Rules.View.RulesViewDTO;
import entity.Rules;

public class RuleMapper {
    public static RulesControllerDTO map(RulesViewDTO rule) {
        return RulesControllerDTO.builder()
                .idRules(rule.idRules())
                .textRules(rule.textRules())
                .build();
    }

    public static RulesViewDTO map(RulesControllerDTO rule) {
        return RulesViewDTO.builder()
                .idRules(rule.idRules())
                .textRules(rule.textRules())
                .build();
    }

    public static RulesControllerDTO map(Rules rule) {
        return RulesControllerDTO.builder()
                .idRules(rule.getIdRules())
                .textRules(rule.getTextRules())
                .build();
    }
}
