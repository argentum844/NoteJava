package servlets.user.update;

import dto.Users.Controller.UserConstFieldsControllerDTO;
import dto.Users.Controller.UserUpdateDescriptionControllerDTO;
import dto.Users.Controller.UsersControllerDTO;
import dto.Users.View.UserUpdateDescriptionViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ServiceManager;
import managers.ValidationManager;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.UserExistRepository;
import service.UserService;
import treats.updates.UpdateStatusUser;
import treats.validators.UpdateUserValidator;
import treats.validators.load.LoadValidationResult;
import utils.JSPPathCreator;
import utils.exceptions.DBExceptions;
import utils.exceptions.ServiceException;

import java.io.IOException;
import java.util.Objects;

import static utils.AttributeGetter.NAME_ATTRIBUTE_ERRORS;
import static utils.AttributeGetter.NAME_ATTRIBUTE_USER;
import static utils.JSPPathGetter.USER_UPDATE_DESCRIPTION_JSP;
import static utils.URLPathGetter.USER_DEFAULT_URL;
import static utils.URLPathGetter.USER_UPDATE_DESCRIPTION_URL;

@WebServlet(USER_UPDATE_DESCRIPTION_URL)
public class UserUpdateDescriptionServlet extends HttpServlet {
    Logger logger = LogManager.getLogger();
    private UserService userService;
    private UpdateUserValidator updateUserValidator;
    private UpdateStatusUser updatedUserFields;

    @Override
    public void init() throws ServletException {
        userService = new ServiceManager().userService;
        updateUserValidator = new ValidationManager().updateUserValidator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getUserPath(USER_UPDATE_DESCRIPTION_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final UsersControllerDTO user = (UsersControllerDTO) req.getSession().getAttribute(NAME_ATTRIBUTE_USER);
            final String login = req.getParameter("login");
            final String name = req.getParameter("userName");
            final String surname = req.getParameter("userSurname");
            final String birthdayDate = req.getParameter("birthdayDate");
            logger.error("login, name, surname, birthdayDate: " + login + ", " + name + ", " + surname + ", " + birthdayDate);

            final UpdateStatusUser updatedUserFields = getUpdatedUserFields(login, user,
                    birthdayDate, name, surname);

            final UserUpdateDescriptionViewDTO userUpdateDescriptionViewDto = getUserUpdateDto(login,
                    name, surname, birthdayDate, user.idUser());

            final LoadValidationResult result = updateUserValidator.isValidDescription(new UserExistRepository(), userUpdateDescriptionViewDto,
                    updatedUserFields);

            if (result.getLoadValidationErrors().isEmpty()) {
                logger.error(userUpdateDescriptionViewDto);
                final UserUpdateDescriptionControllerDTO userUpdateDescriptionControllerDto =
                        UserMapper.map(userUpdateDescriptionViewDto);

                final UserConstFieldsControllerDTO userConstFieldsControllerDto = UserConstFieldsControllerDTO.builder()
                        .idUser(user.idUser())
                        .isAdmin(user.isAdmin())
                        .build();

                final UsersControllerDTO userControllerDto = userService.updateDescriptionUser
                        (userUpdateDescriptionControllerDto, updatedUserFields, userConstFieldsControllerDto);

                req.getSession().removeAttribute(NAME_ATTRIBUTE_USER);
                req.getSession().setAttribute(NAME_ATTRIBUTE_USER, userControllerDto);
                resp.sendRedirect("/NoteJava_war" + USER_DEFAULT_URL);

            } else {
                logger.error(NAME_ATTRIBUTE_ERRORS + result.getLoadValidationErrors());
                req.setAttribute(NAME_ATTRIBUTE_ERRORS, result.getLoadValidationErrors());
                doGet(req, resp);
            }
        }catch (DBExceptions | ServiceException e){
            throw new RuntimeException(e);
        }
    }

    private static UserUpdateDescriptionViewDTO getUserUpdateDto(String nickname, String firstName, String lastName,
                                                                 String birthDate, long idUser) {
        return UserUpdateDescriptionViewDTO.builder()
                .idUser(idUser)
                .login(nickname)
                .userName(firstName)
                .userSurname(lastName)
                .birthdayDate(birthDate)
                .build();
    }

    private static UpdateStatusUser getUpdatedUserFields(String login, UsersControllerDTO user,
                                                         String birthDate, String name, String Surname) {
        return UpdateStatusUser.builder()
                .isNewLogin(!Objects.equals(login, user.login()))
                .isNewBirthdayDate(!Objects.equals(birthDate, user.birthdayDate().toString()))
                .isNewUserName(!Objects.equals(name, user.userName()))
                .isNewUserSurname(!Objects.equals(Surname, user.userSurname()))
                .build();
    }
}
