package servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ServiceManager;
import service.UserService;
import utils.exceptions.DBExceptions;

import java.io.IOException;

public class AdminDeleteServlet  extends HttpServlet {
    protected UserService userService;


    @Override
    public void init() throws ServletException {
        userService = new ServiceManager().userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idUser = req.getParameter("userId");
            if (userService.deleteUser(Long.parseLong(idUser))) {
                req.setAttribute("message", "The user with the id " + idUser + " has been successfully deleted");
            } else {
                req.setAttribute("message", "error. The user with the id " + idUser + " has not been deleted");
            }
            doGet(req, resp);
        } catch (DBExceptions e){
            throw new RuntimeException(e);
        }
    }
}