package pl.kotczykod.invoicing.invoicingapp.db.memory;

import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.model.Invoice;

import java.util.*;

public class InMemoryDatabase implements Database {
    private Map<Long, Invoice> invoices = new HashMap<>();
    Long nextId = 1l;

    @Override
    public Long save(final Invoice invoice) {
        invoice.setId(nextId);
        invoices.put(nextId, invoice);

        return nextId++;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public Optional<Invoice> findInvoiceById(final Long id) {
        return Optional.ofNullable(invoices.get(id));
    }

    @Override
    public void update(final Long id, final Invoice invoice) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("Id :" + id + " does not exists.");
        }

        invoice.setId(id);
        invoices.put(id, invoice);
    }

    @Override
    public void delete(final Long id) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("Id :" + id + " does not exists.");
        }

        invoices.remove(id);
    }
}
