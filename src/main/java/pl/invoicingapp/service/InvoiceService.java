package pl.invoicingapp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.invoicingapp.db.Database;
import pl.invoicingapp.model.Invoice;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final Database database;

    public InvoiceService(@Qualifier("fileBasedDatabase") Database database) {
        this.database = database;
    }

    public Long save(Invoice invoice) {
        return database.save(invoice);
    }

    public Optional<Invoice> findByInvoiceId(Long id) {
        return database.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return database.getAll();
    }

    public Optional<Invoice> update(Long id, Invoice updatedInvoice) {
        return database.update(id, updatedInvoice);
    }

    public Optional<Invoice> delete(Long id) {
        return database.delete(id);
    }
}
