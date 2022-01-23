package pl.invoicingapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.invoicingapp.model.Invoice;
import pl.invoicingapp.service.InvoiceService;

import java.util.List;

@RestController
@AllArgsConstructor
class InvoiceController implements InvoiceApi {
    private final InvoiceService service;

    @Override
    public List<Invoice> getAll() {
        return service.getAllInvoices();
    }

    @Override
    public ResponseEntity<Invoice> getById(@PathVariable Long id) {
        return service.findByInvoiceId(id)
                .map(invoice -> ResponseEntity.ok().body(invoice))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Long add(@RequestBody Invoice invoice) {
        return service.save(invoice);
    }

    @Override
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return service.delete(id)
                .map(invoice -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
        return service.update(id, updatedInvoice)
                .map(invoice -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
