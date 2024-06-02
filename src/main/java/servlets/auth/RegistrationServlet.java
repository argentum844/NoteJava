package servlets.auth;

import dto.Users.Controller.UserInsertControllerDTO;
import dto.Users.View.UserInsertViewDTO;
import jakarta.servlet.ServletConfig;
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
import treats.validators.InsertUserValidator;
import treats.validators.load.LoadValidationResult;
import utils.JSPPathCreator;
import utils.exceptions.DBExceptions;

import java.io.IOException;

import static utils.AttributeGetter.NAME_ATTRIBUTE_ERRORS;
import static utils.JSPPathGetter.REGISTRATION_JSP;
import static utils.URLPathGetter.LOGIN_URL;
import static utils.URLPathGetter.REGISTRATION_URL;

@WebServlet(REGISTRATION_URL)
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;
    private InsertUserValidator registrationUserValidator;


    @Override
    public void init(ServletConfig config) {
        userService = new ServiceManager().userService;
        registrationUserValidator = new ValidationManager().insertUserValidator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getDefaultPath(REGISTRATION_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            final UserInsertViewDTO userRegistrationViewDto = getUserRegistrationViewDto(req);
            final LoadValidationResult result = registrationUserValidator.isValid(new UserExistRepository(), userRegistrationViewDto);
            logger.error(userRegistrationViewDto.isAdmin() + " This is View Admin");
            if (result.isEmpty()) {
                UserInsertControllerDTO userRegistrationControllerDto = UserMapper.map(userRegistrationViewDto);
                logger.error(userRegistrationControllerDto.isAdmin() + " This is Admin");
                userService.insertUser(userRegistrationControllerDto);
                resp.sendRedirect("/NoteJava_war" + LOGIN_URL);
            } else {
                req.setAttribute(NAME_ATTRIBUTE_ERRORS, result.getLoadValidationErrors());
                doGet(req, resp);
            }
        } catch (DBExceptions e){
            logger.error("Error at registration servlet with DBException \n" + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private static UserInsertViewDTO getUserRegistrationViewDto(HttpServletRequest req) {
        return UserInsertViewDTO.builder()
                .login(req.getParameter("login"))
                .userName(req.getParameter("userName"))
                .userSurname(req.getParameter("userSurname"))
                .isAdmin("false")
                .userPassword(req.getParameter("password"))
                .birthdayDate(req.getParameter("birthdayDate"))
                .build();
    }
}
