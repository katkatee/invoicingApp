package pl.invoicingapp.db.memory;

import org.springframework.context.annotation.Primary;
import pl.invoicingapp.db.Database;
import pl.invoicingapp.model.Invoice;

import java.util.*;

@Primary
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
    public List<Invoice> getAll() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public Optional<Invoice> findById(final Long id) {
        return Optional.ofNullable(invoices.get(id));
    }

    @Override
    public Optional<Invoice> update(final Long id, final Invoice updatedInvoice) {
        updatedInvoice.setId(id);
        return Optional.ofNullable(invoices.put(id, updatedInvoice));
    }

    @Override
    public Optional<Invoice> delete(final Long id) {

        return Optional.ofNullable(invoices.remove(id));
    }
}
