package entity;

import java.time.LocalDate;

public class Documents {
    private long idDocument;
    private long idAuthor;
    private String authorDisplayed;
    private String title;
    private String description;
    private String lincTextDocument;
    private LocalDate dateCreate;
    private boolean isPublic;

    public Documents(long idDocument, long idAuthor, String authorDisplayed, String title,
              String description, String lincTextDocument, LocalDate dateCreate, boolean isPublic) {
        this.idDocument = idDocument;
        this.idAuthor = idAuthor;
        this.authorDisplayed = authorDisplayed;
        this.title = title;
        this.description = description;
        this.lincTextDocument = lincTextDocument;
        this.dateCreate = dateCreate;
        this.isPublic = isPublic;
    }
    public Documents(long idAuthor, String authorDisplayed, String title,
                     String description, String lincTextDocument, LocalDate dateCreate, boolean isPublic){
        this.idDocument = 0;
        this.idAuthor = idAuthor;
        this.authorDisplayed = authorDisplayed;
        this.title = title;
        this.description = description;
        this.lincTextDocument = lincTextDocument;
        this.dateCreate = dateCreate;
        this.isPublic = isPublic;
    }

    public long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(long idDocument) {
        this.idDocument = idDocument;
    }

    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getAuthorDisplayed() {
        return authorDisplayed;
    }

    public void setAuthorDisplayed(String authorDisplayed) {
        this.authorDisplayed = authorDisplayed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLincTextDocument() {
        return lincTextDocument;
    }

    public void setLincTextDocument(String lincTextDocument) {
        this.lincTextDocument = lincTextDocument;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "idDocument=" + idDocument +
                ", idAuthor=" + idAuthor +
                ", authorDisplayed='" + authorDisplayed + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", lincTextDocumemt='" + lincTextDocument + '\'' +
                ", dateCreate=" + dateCreate +
                ", isPublic=" + isPublic +
                '}';
    }
}
