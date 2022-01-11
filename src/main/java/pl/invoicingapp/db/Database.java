package pl.invoicingapp.db;

import pl.invoicingapp.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface Database {
    Long save(Invoice invoice);

    List<Invoice> getAllInvoices();

    Optional<Invoice> findInvoiceById(Long id);

    void update(Long id, Invoice invoice);

    void delete(Long id);
}
