package pl.kotczykod.invoicing.invoicingapp.db.file;

import pl.kotczykod.invoicing.invoicingapp.utils.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class IdService {
    private final Path idFilePath;
    private final FileService service;

    private Long nextId = 1l;

    IdService(final Path pathIdFile, final FileService service) {
        this.idFilePath = pathIdFile;
        this.service = service;

    try {
        List<String> lines = service.readAllLines(idFilePath);
        if (lines.isEmpty()) {
            service.writeToFile(idFilePath, "1");
        } else {
            nextId = Long.parseLong(lines.get(0));
        }
    } catch (IOException ex) {
        throw new RuntimeException("Failed to initialize id database", ex);
    }

}

    public Long getNextIdAndIncrement() {
        try {
            service.writeToFile(idFilePath, String.valueOf(nextId + 1));
            return nextId++;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read id file", ex);
        }
    }



}
