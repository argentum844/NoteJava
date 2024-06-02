package service;

import dto.Documents.View.DocumentInsertDTO;
import entity.Documents;
import mapper.DocumentMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DocumentsRepository;
import treats.validators.CreateDocumentValidator;
import treats.validators.load.LoadValidationResult;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DocumentService {
    private static final Logger logger = LogManager.getLogger();
    private final DocumentsRepository documentsRepository;
    private final CreateDocumentValidator createDocumentValidator = new CreateDocumentValidator();

    public DocumentService(DocumentsRepository documentsRepository){
        this.documentsRepository = documentsRepository;
    }

    public LoadValidationResult insertDocument(DocumentInsertDTO dto){
        String linqDocument = filePathCreator(dto);
        LoadValidationResult res = createDocumentValidator.isValid(dto);
        if (res.isEmpty()) {
            writeToFile(dto.textDocument(), linqDocument);

            Documents documents = DocumentMapper.map(dto, linqDocument);
            documentsRepository.insert(documents);
            logger.error(readFromFile(linqDocument));
        }
        return res;
    }


    public void writeToFile(String text, String filename){

        try(FileWriter writer = new FileWriter(filename, false)){
            writer.write(text);
            logger.error("Write in a file: " + filename);
            writer.flush();
        } catch (IOException ex){
            logger.error(ex);
        }
    }
    public String readFromFile(String filename){
        try (FileReader reader = new FileReader(filename)){
            return Files.readString(Paths.get(filename));
        }catch (IOException ex){
            logger.error(ex);
            return null;
        }
    }
    public String filePathCreator(DocumentInsertDTO dto){
        String linqDocument = dto.idAuthor() + "_" + dto.dateCreate().toString() + '_' + dto.title() + ".txt";

        return linqDocument;
    }
}
