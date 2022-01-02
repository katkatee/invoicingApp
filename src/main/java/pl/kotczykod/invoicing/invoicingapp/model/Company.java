package pl.kotczykod.invoicing.invoicingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private Long id;
    private String taxIdentificationNumber;
    private String address;
}
