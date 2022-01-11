package pl.invoicingapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.invoicingapp.db.memory.InMemoryDatabase;
import pl.invoicingapp.model.Invoice;
import pl.invoicingapp.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoices/api")
class InvoiceController {
    private final InvoiceService service = new InvoiceService(new InMemoryDatabase());

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return service.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable Long id) {
        return service.findByInvoiceId(id)
                .map(invoice -> ResponseEntity.ok().body(invoice))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Long add(@RequestBody Invoice invoice) {
        return service.save(invoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return service.delete(id)
                .map(invoice -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
        return service.update(id, updatedInvoice)
                .map(invoice -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
