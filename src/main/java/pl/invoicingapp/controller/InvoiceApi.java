package pl.invoicingapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.invoicingapp.model.Invoice;

import java.util.List;

@RequestMapping("/invoices/api")
@Api(tags = {"invoice-controller"})
public interface InvoiceApi {

    @ApiOperation(value = "Get list of all invoices.")
    @GetMapping(produces = {"application/json;charset=UTF-8"})
    List<Invoice> getAll();

    @ApiOperation(value = "Add new invoice to system.")
    @PostMapping
    Long add(@RequestBody Invoice invoice);

    @ApiOperation(value = "Get invoice by id.")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    ResponseEntity<Invoice> getById(@PathVariable Long id);

    @ApiOperation(value = "Delete invoice with given id.")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);

    @ApiOperation(value = "Update invoice with given id.")
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Invoice invoice);

}
