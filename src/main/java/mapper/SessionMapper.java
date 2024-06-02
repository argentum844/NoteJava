package mapper;

import dto.Sessions.Controller.SessionControllerDTO;
import dto.Sessions.View.SessionViewDTO;
import entity.Sessions;
import mapper.converter.ConvertString;

import java.text.ParseException;

public class SessionMapper {
    public static SessionControllerDTO map(SessionViewDTO session) throws ParseException {
        return SessionControllerDTO.builder()
                .idSession(session.idSession())
                .idUser(session.idUser())
                .dateStart(ConvertString.toDate(session.dateStart()))
                .dateEnd(ConvertString.toDate(session.dateEnd()))
                .build();
    }

    public static SessionViewDTO map(SessionControllerDTO session) throws ParseException {
        return SessionViewDTO.builder()
                .idSession(session.idSession())
                .idUser(session.idUser())
                .dateStart(ConvertString.dateToString(session.dateStart()))
                .dateEnd(ConvertString.dateToString(session.dateEnd()))
                .build();
    }

    public static SessionControllerDTO map(Sessions session) throws ParseException {
        return SessionControllerDTO.builder()
                .idSession(session.getIdSession())
                .idUser(session.getIdUser())
                .dateStart(session.getDateStart())
                .dateEnd(session.getDateEnd())
                .build();
    }
}
