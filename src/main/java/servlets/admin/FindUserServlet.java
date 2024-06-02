package servlets.admin;


import dto.Users.Controller.UserForAdminControllerDTO;
import dto.Users.View.UserForAdminViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ServiceManager;
import mapper.UserMapper;
import service.UserService;
import utils.JSPPathCreator;
import utils.exceptions.DBExceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static utils.JSPPathGetter.ADMIN_USER_FIND_JSP;
import static utils.URLPathGetter.ADMIN_USER_FIND_URL;

@WebServlet(ADMIN_USER_FIND_URL)
public class FindUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new ServiceManager().userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getAdminPath(ADMIN_USER_FIND_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        UserForAdminControllerDTO userForAdminControllerDto = null;
        try {
            userForAdminControllerDto = userService.selectUserByLogin(login);
        } catch (DBExceptions | SQLException e) {
            throw new RuntimeException(e);
        }
        if (userForAdminControllerDto == null) {
            req.setAttribute("messageNotFound", "The user with the login " + login + " is missing");
        } else {
            UserForAdminViewDTO userForAdminViewDto = UserMapper
                    .map(userForAdminControllerDto);
            req.setAttribute("userDto", userForAdminViewDto);
        }
        doGet(req, resp);
    }
}
