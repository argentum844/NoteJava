package servlets.admin;

import dto.Users.Controller.UsersControllerDTO;
import dto.Users.View.UsersViewDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import utils.JSPPathCreator;

import java.io.IOException;

import static utils.AttributeGetter.NAME_ATTRIBUTE_USER;
import static utils.AttributeGetter.NAME_ATTRIBUTE_USER_DTO;
import static utils.JSPPathGetter.ADMIN_DEFAULT_JSP;
import static utils.URLPathGetter.ADMIN_DEFAULT_URL;

@WebServlet(ADMIN_DEFAULT_URL)
public class AdminDefaultServlet extends HttpServlet {
//    private UserMapper userMapper;
//
//    @Override
//    public void init() throws ServletException {
//        userMapper = MapperManager.getUserMapper();
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UsersControllerDTO userControllerDto =
                (UsersControllerDTO) req.getSession().getAttribute(NAME_ATTRIBUTE_USER);
        UsersViewDTO user = UserMapper.map(userControllerDto);
        req.setAttribute(NAME_ATTRIBUTE_USER_DTO, user);
        req.getRequestDispatcher(JSPPathCreator.getAdminPath(ADMIN_DEFAULT_JSP)).forward(req, resp);
    }
}
