package pl.kotczykod.invoicing.invoicingapp.db.file;

import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.model.Invoice;
import pl.kotczykod.invoicing.invoicingapp.utils.FileService;
import pl.kotczykod.invoicing.invoicingapp.utils.JsonService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class FileBasedDatabase implements Database {
    private final Path dbPath;
    private final IdService idService;
    private final FileService service;
    private final JsonService jsonService;

    public FileBasedDatabase(Path dbPath, IdService idService, FileService service, JsonService jsonService) {
        this.dbPath = dbPath;
        this.idService = idService;
        this.service = service;
        this.jsonService = jsonService;
    }


    @Override
    public Long save(final Invoice invoice) {
        try {
            invoice.setId(idService.getNextIdAndIncrement());
            service.appendLineToFile(dbPath, jsonService.toJson(invoice));
            return invoice.getId();
        } catch (IOException e) {
            throw new RuntimeException("Database failed to save invoice", e);
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        try {
            return service.readAllLines(dbPath).stream()
                    .map(line -> jsonService.toObject(line, Invoice.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoices from file", e);
        }
    }

    @Override
    public Optional<Invoice> findInvoiceById(final Long id) {

        try {
            return service.readAllLines(dbPath)
                    .stream()
                    .filter(line -> containsId(line, id))
                    .map(line -> jsonService.toObject(line, Invoice.class))
                    .findFirst();

        } catch (IOException e) {
            throw new RuntimeException("Database failed to get invoice with id: " + id, e);
        }
    }

    @Override
    public void update(final Long id, final Invoice invoice) {
        try {
            List<String> allInvoices = service.readAllLines(dbPath);
            var listWithoutInvoiceWithGivenId = allInvoices.stream()
                    .filter(line -> !containsId(line, id))
                    .collect(Collectors.toList());

            if (allInvoices.size() == listWithoutInvoiceWithGivenId.size()) {
                throw new IllegalArgumentException("Id " + id + " does not exist");
            }

            invoice.setId(id);
            listWithoutInvoiceWithGivenId.add(jsonService.toJson(invoice));

            service.writeLinesToFile(dbPath, listWithoutInvoiceWithGivenId);

        } catch (IOException e) {
            throw new RuntimeException("Database failed to update invoice with id: " + id, e);
        }

    }

    @Override
    public void delete(final Long id) {

        try {
            List<String> allInvoices = service.readAllLines(dbPath);
            var listWithoutInvoiceWithGivenId = allInvoices
                    .stream()
                    .filter(line -> !containsId(line, id))
                    .collect(Collectors.toList());

            if (allInvoices.size() == listWithoutInvoiceWithGivenId.size()) {
                throw new IllegalArgumentException("Id " + id + " does not exist");
            }

            service.writeLinesToFile(dbPath, listWithoutInvoiceWithGivenId);

        } catch (IOException e) {
            throw new RuntimeException("Database failed to delete invoice with id: " + id, e);
        }
    }

    private boolean containsId(String line, Long id) {
        return line.contains("\"id\":" + id + "," );
    }
}
