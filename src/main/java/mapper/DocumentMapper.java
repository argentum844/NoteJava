package mapper;

import dto.Documents.Controller.DocumentsControllerDTO;
import dto.Documents.View.DocumentInsertDTO;
import dto.Documents.View.DocumentsViewDTO;
import entity.Documents;
import mapper.converter.ConvertString;

import java.text.ParseException;

public class DocumentMapper {
    public static DocumentsControllerDTO map(DocumentsViewDTO document) throws ParseException {
        return DocumentsControllerDTO.builder()
                .idDocument(document.idDocument())
                .idAuthor(document.idAuthor())
                .authorDisplayed(document.authorDisplayed())
                .title(document.title())
                .description(document.description())
                .lincTextDocument(document.lincTextDocument())
                .dateCreate(ConvertString.toDate(document.dateCreate()))
                .isPublic(ConvertString.toBoolean(document.isPublic()))
                .build();
    }

    public static DocumentsViewDTO map(DocumentsControllerDTO document) throws ParseException {
        return DocumentsViewDTO.builder()
                .idDocument(document.idDocument())
                .idAuthor(document.idAuthor())
                .authorDisplayed(document.authorDisplayed())
                .title(document.title())
                .description(document.description())
                .lincTextDocument(document.lincTextDocument())
                .dateCreate(ConvertString.dateToString(document.dateCreate()))
                .isPublic(String.valueOf(document.isPublic()))
                .build();
    }

    public static DocumentsControllerDTO map(Documents document) throws ParseException {
        return DocumentsControllerDTO.builder()
                .idDocument(document.getIdDocument())
                .idAuthor(document.getIdAuthor())
                .authorDisplayed(document.getAuthorDisplayed())
                .title(document.getTitle())
                .description(document.getDescription())
                .lincTextDocument(document.getLincTextDocument())
                .dateCreate(document.getDateCreate())
                .isPublic(document.isPublic())
                .build();
    }
    public static Documents map(DocumentInsertDTO dto, String linqDocument){
        return new Documents(dto.idAuthor(), dto.authorDisplayed(), dto.title(), dto.description(),
                linqDocument, dto.dateCreate(), dto.isPublic());
    }
}
