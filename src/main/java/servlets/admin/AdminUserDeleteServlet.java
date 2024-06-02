package servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSPPathCreator;

import java.io.IOException;

import static utils.JSPPathGetter.ADMIN_USER_FIND_JSP;
import static utils.URLPathGetter.ADMIN_USER_DELETE_URL;

@WebServlet(ADMIN_USER_DELETE_URL)
public class AdminUserDeleteServlet extends AdminDeleteServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPPathCreator.getAdminPath(ADMIN_USER_FIND_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
