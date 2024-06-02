package mapper;

import dto.Editors.Controller.EditorsControllerDTO;
import dto.Editors.View.EditorsViewDTO;
import entity.Editors;

public class EditorMapper {
    public static EditorsControllerDTO map(EditorsViewDTO editor) {
        return EditorsControllerDTO.builder()
                .idEditor(editor.idEditor())
                .idDocument(editor.idDocument())
                .build();
    }

    public static EditorsControllerDTO map(Editors editor) {
        return EditorsControllerDTO.builder()
                .idEditor(editor.getIdEditor())
                .idDocument(editor.getIdDocument())
                .build();
    }

    public static EditorsViewDTO map(EditorsControllerDTO editor) {
        return EditorsViewDTO.builder()
                .idEditor(editor.idEditor())
                .idDocument(editor.idDocument())
                .build();
    }
}
