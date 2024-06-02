package servlets.user.update;

import dto.Users.Controller.UserLogoPassControllerDTO;
import dto.Users.Controller.UsersControllerDTO;
import dto.Users.View.UserLogoPassViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ServiceManager;
import mapper.UserMapper;
import service.UserService;
import treats.validators.logopass.UpdatePasswordError;
import utils.JSPPathCreator;
import utils.exceptions.DBExceptions;
import utils.exceptions.ServiceException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static utils.AttributeGetter.NAME_ATTRIBUTE_ERROR;
import static utils.AttributeGetter.NAME_ATTRIBUTE_USER;
import static utils.JSPPathGetter.USER_UPDATE_PASSWORD_JSP;
import static utils.URLPathGetter.USER_DEFAULT_URL;
import static utils.URLPathGetter.USER_UPDATE_PASSWORD_URL;

@WebServlet(USER_UPDATE_PASSWORD_URL)
public class UserUpdatePasswordServlet extends HttpServlet {
    private static UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new ServiceManager().userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getUserPath(USER_UPDATE_PASSWORD_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersControllerDTO user = (UsersControllerDTO) req.getSession().getAttribute(NAME_ATTRIBUTE_USER);
        String login = user.login();
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        UserLogoPassViewDTO userLogoPassViewDto = UserLogoPassViewDTO.builder()
                .login(login)
                .oldPass(oldPassword)
                .newPass(newPassword)
                .build();
        UserLogoPassControllerDTO userLogoPasControllerDto = UserMapper.map(userLogoPassViewDto);
        try {
            Optional<UpdatePasswordError> error = userService.updatePasswordError(userLogoPasControllerDto);

            if (error.isEmpty()) {
                resp.sendRedirect("/NoteJava_war" + USER_DEFAULT_URL);
            } else {
                req.setAttribute(NAME_ATTRIBUTE_ERROR, error.get());
                doGet(req, resp);
            }
        } catch (DBExceptions | SQLException | ServiceException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
