package servlets.user.profile;

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
import static utils.JSPPathGetter.USER_DEFAULT_JSP;
import static utils.URLPathGetter.USER_DEFAULT_URL;

@WebServlet(USER_DEFAULT_URL)
public class UserDefaultServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UsersControllerDTO userControllerDto =
                (UsersControllerDTO) req.getSession().getAttribute(NAME_ATTRIBUTE_USER);
        UsersViewDTO user = UserMapper.map(userControllerDto);
        req.setAttribute(NAME_ATTRIBUTE_USER_DTO, user);
        req.getRequestDispatcher(JSPPathCreator.getUserPath(USER_DEFAULT_JSP)).forward(req, resp);
    }

}