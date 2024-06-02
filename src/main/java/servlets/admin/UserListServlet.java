package servlets.admin;

import dto.Users.View.UserForAdminViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import utils.JSPPathCreator;
import utils.exceptions.DBExceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utils.JSPPathGetter.ADMIN_USER_LIST_JSP;
import static utils.URLPathGetter.ADMIN_USER_LIST_URL;

@WebServlet(ADMIN_USER_LIST_URL)
public class UserListServlet extends AdminDeleteServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<UserForAdminViewDTO> userForAdminViewDtoList = userService.selectAllUsers()
                    .stream()
                    .map(UserMapper::map)
                    .toList();
            req.setAttribute("userForAdminViewDtoList", userForAdminViewDtoList);
            req.getRequestDispatcher(JSPPathCreator.getAdminPath(ADMIN_USER_LIST_JSP)).forward(req, resp);
        } catch (DBExceptions | SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
