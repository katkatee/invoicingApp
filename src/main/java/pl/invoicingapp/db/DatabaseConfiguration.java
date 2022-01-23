package pl.invoicingapp.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.invoicingapp.db.file.FileBasedDatabase;
import pl.invoicingapp.db.file.IdService;
import pl.invoicingapp.db.memory.InMemoryDatabase;
import pl.invoicingapp.utils.FileService;
import pl.invoicingapp.utils.JsonService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Configuration
class DatabaseConfiguration {

    @Bean
    public IdService idService(
            FileService fileService,
            @Value("${invoicing-system.database.directory") String databaseDirectory,
            @Value("${invoicing-system.database.id.file") String idFile) throws IOException {
        log.info("Creating idService.");
        Path idFilePath = Files.createTempFile(databaseDirectory, idFile);
        return new IdService(idFilePath, fileService);
    }

    @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
    @Bean
    public Database fileBasedDatabase(
            IdService idService,
            FileService fileService,
            JsonService jsonService,
            @Value("${invoicing-system.database.directory") String databaseDirectory,
            @Value("${invoicing-system.database.invoices.file") String invoicesFile) throws IOException {
        log.info("Creating in-file database.");
        Path databaseFilePath = Files.createTempFile(databaseDirectory, invoicesFile);
        return new FileBasedDatabase(databaseFilePath, idService, fileService, jsonService);
    }

    @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "memory")
    @Bean
    public Database inMemoryDatabase() {
        log.info("Creating in-memory database.");
        return new InMemoryDatabase();
    }
}