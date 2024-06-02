package servlets.auth;

import dto.Users.Controller.UserLoginControllerDTO;
import dto.Users.Controller.UsersControllerDTO;
import dto.Users.View.UserLoginViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ServiceManager;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import treats.validators.logopass.LoginError;
import utils.JSPPathCreator;
import utils.URLPathGetter;
import utils.exceptions.DBExceptions;
import utils.exceptions.ServiceException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Optional;

import static utils.AttributeGetter.NAME_ATTRIBUTE_ERROR;
import static utils.AttributeGetter.NAME_ATTRIBUTE_USER;
import static utils.JSPPathGetter.LOGIN_JSP;
import static utils.URLPathGetter.LOGIN_URL;

@WebServlet(LOGIN_URL)
public class LoginServlet extends HttpServlet {
    private UserService userService;
    Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        userService = new ServiceManager().userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getDefaultPath(LOGIN_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final UserLoginViewDTO userLoginViewDto = new UserLoginViewDTO
                    (req.getParameter("login"), req.getParameter("userPassword"));
            final UserLoginControllerDTO userLoginControllerDto = UserMapper.map(userLoginViewDto);
            Optional<LoginError> error = userService.checkLogin(userLoginControllerDto);
            if (error.isEmpty()) {
                final UsersControllerDTO userControllerDto = userService.selectUser(userLoginControllerDto);
                req.getSession().setAttribute(NAME_ATTRIBUTE_USER, userControllerDto);
                if (!userControllerDto.isAdmin()) {
                    resp.sendRedirect("/NoteJava_war" + URLPathGetter.USER_DEFAULT_URL);
                } else {
                    resp.sendRedirect("/NoteJava_war" + URLPathGetter.ADMIN_DEFAULT_URL);
                }
            } else {
                req.setAttribute(NAME_ATTRIBUTE_ERROR, error.get());
                req.getRequestDispatcher(JSPPathCreator.getDefaultPath(LOGIN_JSP)).forward(req, resp);
            }
        } catch (DBExceptions | SQLException | ServiceException e){
            logger.error("Error in Login Servlet: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}