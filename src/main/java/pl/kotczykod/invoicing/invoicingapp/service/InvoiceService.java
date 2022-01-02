package pl.kotczykod.invoicing.invoicingapp.service;

import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.model.Invoice;

import java.util.List;
import java.util.Optional;

class InvoiceService {
    private final Database database;

    public InvoiceService(Database database) {
        this.database = database;
    }

    public Long save(Invoice invoice) {
        return database.save(invoice);
    }

    public Optional<Invoice> findByInvoiceId(Long id) {
        return database.findInvoiceById(id);
    }

    public List<Invoice> getAllInvoices() {
        return database.getAllInvoices();
    }

    public void update(Long id, Invoice updatedInvoice) {
        database.update(id, updatedInvoice);
    }

    public void delete(Long id) {
        database.delete(id);
    }
}
