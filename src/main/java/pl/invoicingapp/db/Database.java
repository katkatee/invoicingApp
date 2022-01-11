package pl.invoicingapp.db;

import pl.invoicingapp.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface Database {
    Long save(Invoice invoice);

    List<Invoice> getAll();

    Optional<Invoice> findById(Long id);

    Optional<Invoice> update(Long id, Invoice invoice);

    Optional<Invoice> delete(Long id);
}
