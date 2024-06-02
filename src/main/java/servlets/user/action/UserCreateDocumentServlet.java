package servlets.user.action;


import dto.Documents.View.DocumentInsertDTO;
import dto.Users.Controller.UsersControllerDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.DocumentsRepository;
import service.DocumentService;
import treats.validators.load.LoadValidationResult;
import utils.JSPPathCreator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static utils.AttributeGetter.NAME_ATTRIBUTE_ERRORS;
import static utils.AttributeGetter.NAME_ATTRIBUTE_USER;
import static utils.JSPPathGetter.USER_CREATE_DOCUMENT_JSP;
import static utils.URLPathGetter.*;

@WebServlet(USER_CREATE_DOCUMENT_URL)
public class UserCreateDocumentServlet extends HttpServlet {
    private DocumentService documentService;
    @Override

    public void init(){
        documentService = new DocumentService(new DocumentsRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSPPathCreator.getUserPath(USER_CREATE_DOCUMENT_JSP)).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UsersControllerDTO user = (UsersControllerDTO) req.getSession().getAttribute(NAME_ATTRIBUTE_USER);
        DocumentInsertDTO dto = getDocumentInsertViewDTO(req, user);
        LoadValidationResult res = documentService.insertDocument(dto);
        if (!res.isEmpty()){
            req.setAttribute(NAME_ATTRIBUTE_ERRORS, res.getLoadValidationErrors());
            doGet(req, resp);
        } else {
            resp.sendRedirect("/NoteJava_war" + USER_DEFAULT_URL);
        }
    }

    private DocumentInsertDTO getDocumentInsertViewDTO(HttpServletRequest req, UsersControllerDTO user){
        return DocumentInsertDTO.builder()
                .dateCreate(LocalDate.now())
                .authorDisplayed(user.login())
                .description("")
                .idAuthor(user.idUser())
                .isPublic(false)
                .textDocument(req.getParameter("documentText"))
                .title(req.getParameter("title"))
                .build();
    }
}
