package pl.invoicingapp.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.invoicingapp.model.Invoice;

import static pl.invoicingapp.Entries.invoice1;

class JsonServiceTest {

    @Test
    @DisplayName("Can convert object to json and read it back")
    void test1() {
        //given
        JsonService service = new JsonService();

        //when
        String invoiceAsString = service.toJson(invoice1);
        Invoice invoiceAsObject = service.toObject(invoiceAsString, Invoice.class);

        //then
        Assertions.assertEquals(invoiceAsObject, invoice1);
        System.out.println(service.toJson(invoice1));
    }
}
