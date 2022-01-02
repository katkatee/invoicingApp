package pl.kotczykod.invoicing.invoicingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private Long id;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;

}
