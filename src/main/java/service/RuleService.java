package service;

import dto.Rules.Controller.RulesControllerDTO;
import mapper.RuleMapper;
import repository.RulesRepository;
import utils.exceptions.DBExceptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RuleService {
    private final RuleMapper mapper;
    private final RulesRepository repository;

    RuleService(RuleMapper mapper, RulesRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

//    public List<RulesControllerDTO> selectAllRules(String filter){
//        try {
//            ResultSet res = repository.getAll(filter);
//            List<RulesControllerDTO> list = new LinkedList<>();
//            while (res.next()){
//
//            }
//        } catch (DBExceptions | SQLException e){
//            return null;
//        }
//    }
}