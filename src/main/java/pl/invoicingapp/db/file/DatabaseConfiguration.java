package pl.invoicingapp.db.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.invoicingapp.db.Database;
import pl.invoicingapp.utils.FileService;
import pl.invoicingapp.utils.JsonService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
class DatabaseConfiguration {
    public static final String DATABASE_LOCATION = "db";
    public static final String ID_FILE_NAME = "id.txt";
    public static final String INVOICES_FILE_NAME = "invoices.txt";

    @Bean
    public IdService idService(FileService fileService) throws IOException {
        Path idFilePath = Files.createTempFile(DATABASE_LOCATION, ID_FILE_NAME);
        return new IdService(idFilePath, fileService);
    }

    @Bean
    public Database fileBasedDatabase(IdService idService, FileService fileService, JsonService jsonService) throws IOException {
        Path databaseFilePath = Files.createTempFile(DATABASE_LOCATION, INVOICES_FILE_NAME);
        return new FileBasedDatabase(databaseFilePath, idService, fileService, jsonService);
    }
}