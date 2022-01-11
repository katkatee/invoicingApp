package pl.invoicingapp.db.file;

import lombok.AllArgsConstructor;
import pl.invoicingapp.db.Database;
import pl.invoicingapp.model.Invoice;
import pl.invoicingapp.utils.FileService;
import pl.invoicingapp.utils.JsonService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public
class FileBasedDatabase implements Database {
    private final Path databasePath;
    private final IdService idService;
    private final FileService service;
    private final JsonService jsonService;


    @Override
    public Long save(final Invoice invoice) {
        try {
            invoice.setId(idService.getNextIdAndIncrement());
            service.appendLineToFile(databasePath, jsonService.toJson(invoice));
            return invoice.getId();
        } catch (IOException e) {
            throw new RuntimeException("Database failed to save invoice", e);
        }
    }

    @Override
    public List<Invoice> getAll() {
        try {
            return service.readAllLines(databasePath).stream()
                    .map(line -> jsonService.toObject(line, Invoice.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoices from file", e);
        }
    }

    @Override
    public Optional<Invoice> findById(final Long id) {

        try {
            return service.readAllLines(databasePath)
                    .stream()
                    .filter(line -> containsId(line, id))
                    .map(line -> jsonService.toObject(line, Invoice.class))
                    .findFirst();

        } catch (IOException e) {
            throw new RuntimeException("Database failed to get invoice with id: " + id, e);
        }
    }

    @Override
    public Optional<Invoice> update(final Long id, final Invoice invoice) {
        try {
            List<String> allInvoices = service.readAllLines(databasePath);
            var listWithoutInvoiceWithGivenId = allInvoices.stream()
                    .filter(line -> !containsId(line, id))
                    .collect(Collectors.toList());

            invoice.setId(id);
            listWithoutInvoiceWithGivenId.add(jsonService.toJson(invoice));

            service.writeLinesToFile(databasePath, listWithoutInvoiceWithGivenId);
            return allInvoices.isEmpty() ?
                    Optional.empty() : Optional.of(jsonService.toObject(allInvoices.get(0),Invoice.class));

        } catch (IOException e) {
            throw new RuntimeException("Database failed to update invoice with id: " + id, e);
        }

    }

    @Override
    public Optional<Invoice> delete(final Long id) {

        try {
            List<String> allInvoices = service.readAllLines(databasePath);
            var listWithoutInvoiceWithGivenId = allInvoices
                    .stream()
                    .filter(line -> !containsId(line, id))
                    .collect(Collectors.toList());

            if (allInvoices.size() == listWithoutInvoiceWithGivenId.size()) {
                throw new IllegalArgumentException("Id " + id + " does not exist");
            }

            service.writeLinesToFile(databasePath, listWithoutInvoiceWithGivenId);
            allInvoices.removeAll(listWithoutInvoiceWithGivenId);

            return allInvoices.isEmpty() ?
                    Optional.empty() : Optional.of(jsonService.toObject(allInvoices.get(0), Invoice.class));

        } catch (IOException e) {
            throw new RuntimeException("Database failed to delete invoice with id: " + id, e);
        }
    }

    private boolean containsId(String line, Long id) {
        return line.contains("\"id\":" + id + "," );
    }
}
