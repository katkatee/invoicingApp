package pl.invoicingapp;

import pl.invoicingapp.model.Company;
import pl.invoicingapp.model.Invoice;
import pl.invoicingapp.model.InvoiceEntry;
import pl.invoicingapp.model.Vat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Entries {


    public static Company buyer = new Company(1l, "7656542343", "Kasztanowa 2/3, 44-323 Warszawa");
    public static Company seller = new Company(2l, "767654239", "Wiklinowa 2/3, 44-323 Warszawa");

    public static InvoiceEntry entry1 = new InvoiceEntry("Pudełko", BigDecimal.valueOf(22.35), BigDecimal.valueOf(5), Vat.VAT_5);
    public static InvoiceEntry entry2 = new InvoiceEntry("Książka", BigDecimal.valueOf(65.25), BigDecimal.valueOf(8), Vat.VAT_8);
    public static InvoiceEntry entry3 = new InvoiceEntry("Kubek", BigDecimal.valueOf(10), BigDecimal.valueOf(23), Vat.VAT_23);
    public static InvoiceEntry entry4 = new InvoiceEntry("Papier", BigDecimal.valueOf(87.05), BigDecimal.valueOf(7), Vat.VAT_7);
    public static Invoice invoice1 = new Invoice(1l, LocalDate.now(), seller, buyer, List.of(entry1, entry2));
    public static Invoice invoice2 = new Invoice(2l, LocalDate.now(), seller, buyer, List.of(entry2, entry3));
    public static Invoice invoice3 = new Invoice(3l, LocalDate.now(), seller, buyer, List.of(entry1, entry4));
    public static Invoice invoice4 = new Invoice(1l, LocalDate.now(), seller, buyer, List.of(entry1));
}
