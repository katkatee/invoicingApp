package pl.kotczykod.invoicing.invoicingapp.db.file;

import lombok.SneakyThrows;
import pl.kotczykod.invoicing.invoicingapp.db.AbstractDatabaseTest;
import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.utils.FileService;
import pl.kotczykod.invoicing.invoicingapp.utils.JsonService;

import java.io.File;
import java.nio.file.Path;

class FileBasedDatabaseIntegrationTest extends AbstractDatabaseTest {

    @SneakyThrows
    @Override
    public Database getDatabaseInstance() {
        FileService service = new FileService();
        Path idPath = File.createTempFile("ids", ".txt").toPath();
        IdService idService = new IdService(idPath, service);
        Path dbPath = File.createTempFile("invoices", ".txt").toPath();

        return new FileBasedDatabase(dbPath, idService, service, new JsonService());
    }
}
