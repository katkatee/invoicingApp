package pl.invoicingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.invoicingapp.service.InvoiceService;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class InvoicingAppApplicationTest {


    @Autowired
    private InvoiceService invoiceService;

    @Test
    void contextLoads() {
        //given, when
        //then
        assertThat(invoiceService).isNotNull();
    }
}