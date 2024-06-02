package entity;

public class Editors {
    private long idEditor;
    private long idDocument;

    Editors(long idEditor, long idDocument) {
        this.idDocument = idDocument;
        this.idEditor = idEditor;
    }

    public long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(long idDocument) {
        this.idDocument = idDocument;
    }

    public long getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(long idEditor) {
        this.idEditor = idEditor;
    }

    @Override
    public String toString() {
        return "Editors{" +
                "idEditor=" + idEditor +
                ", idDocument=" + idDocument +
                '}';
    }
}

