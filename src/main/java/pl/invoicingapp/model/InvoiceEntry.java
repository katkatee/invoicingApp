package pl.invoicingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEntry {
    private String description;
    private BigDecimal price;
    private BigDecimal vatValue;
    private Vat rate;
}
